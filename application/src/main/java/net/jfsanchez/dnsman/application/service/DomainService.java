package net.jfsanchez.dnsman.application.service;

import jakarta.inject.Singleton;
import java.util.function.Supplier;
import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.application.port.input.DomainUseCase;
import net.jfsanchez.dnsman.application.port.output.AuthorizationPort;
import net.jfsanchez.dnsman.application.port.output.DomainPort;
import net.jfsanchez.dnsman.application.port.output.TransactionManagementPort;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.error.RecordAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.RecordDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class DomainService implements DomainUseCase {

  private final DomainPort domainPort;
  private final TransactionManagementPort transactionManagementPort;
  private final AuthorizationPort authorizationPort;

  public DomainService(DomainPort domainPort, TransactionManagementPort transactionManagementPort, AuthorizationPort authorizationPort) {
    this.domainPort = domainPort;
    this.transactionManagementPort = transactionManagementPort;
    this.authorizationPort = authorizationPort;
  }

  @Override
  public Mono<Domain> registerDomain(DomainName domainName) throws DomainAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> withTransaction(
        () -> domainPort.persistDomain(new Domain(domainName))
    ));
  }

  @Override
  public Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException {
    return domainPort.getDomainByName(domainName);
  }

  @Override
  public Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException {
    return domainPort.getDomainById(domainId);
  }

  @Override
  public Flux<Domain> listDomains() {
    return domainPort.listDomains();
  }

  @Override
  public Mono<Domain> removeDomainByName(DomainName domainName) throws DomainDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> withTransaction(
        () -> domainPort.getDomainByName(domainName).flatMap(domainPort::removeDomain)
    ));
  }

  @Override
  public Mono<Domain> removeDomainById(Long domainId) throws DomainDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> withTransaction(
        () -> domainPort.getDomainById(domainId).flatMap(domainPort::removeDomain)
    ));
  }

  @Override
  public Mono<Domain> addDomainRecord(DomainName domainName, Type recordType, String recordValue, Long ttl)
      throws RecordAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> withTransaction(
        () -> domainPort.getDomainByName(domainName)
            .map(domain -> domain.addRecord(recordType, recordValue, ttl))
            .flatMap(domainPort::updateDomain)
    ));
  }

  @Override
  public Mono<Domain> addDomainRecord(Long domainId, Type recordType, String recordValue, Long ttl)
      throws RecordAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> withTransaction(
        () -> domainPort.getDomainById(domainId)
            .map(domain -> domain.addRecord(recordType, recordValue, ttl))
            .flatMap(domainPort::updateDomain)
    ));
  }

  @Override
  public Mono<Domain> removeDomainRecord(DomainName domainName, Type type, String recordValue)
      throws DomainDoesNotExistsException, RecordDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> withTransaction(
        () -> domainPort.getDomainByName(domainName)
            .map(domain -> domain.removeRecord(type, recordValue))
            .flatMap(domainPort::updateDomain)
    ));
  }

  @Override
  public Mono<Domain> removeDomainRecord(Long domainId, Type type, String recordValue)
      throws DomainDoesNotExistsException, RecordDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> withTransaction(
        () -> domainPort.getDomainById(domainId)
            .map(domain -> domain.removeRecord(type, recordValue))
            .flatMap(domainPort::updateDomain)
    ));
  }

  private <T> Mono<T> isAdminUser(Supplier<Publisher<T>> supplier) {
    return authorizationPort.isAdminUser()
        .flatMap(isAdminUser -> {
          if (isAdminUser) {
            return Mono.from(supplier.get());
          } else {
            return Mono.error(new UnauthorizedException());
          }
        })
        ;
  }

  private <T> Mono<T> withTransaction(Supplier<Mono<T>> supplier) {
    return transactionManagementPort.withTransaction(supplier);
  }
}
