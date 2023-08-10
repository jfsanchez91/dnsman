package net.jfsanchez.dnsman.application.service;

import jakarta.inject.Singleton;
import java.util.function.Supplier;
import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.application.port.input.DomainUseCase;
import net.jfsanchez.dnsman.application.port.output.AuthorizationPort;
import net.jfsanchez.dnsman.application.port.output.DomainPort;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.error.RecordAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.RecordDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class DomainService implements DomainUseCase {
  private final DomainPort domainPort;
  private final AuthorizationPort authorizationPort;

  public DomainService(DomainPort domainPort, AuthorizationPort authorizationPort) {
    this.domainPort = domainPort;
    this.authorizationPort = authorizationPort;
  }

  @Override
  public Mono<Domain> registerDomain(DomainName domainName) throws DomainAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> domainPort.persistDomain(new Domain(domainName)));
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
    return isAdminUser(() -> domainPort.getDomainByName(domainName)
        .flatMap(domainPort::removeDomain)
    );
  }

  @Override
  public Mono<Domain> removeDomainById(Long domainId) throws DomainDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> domainPort.getDomainById(domainId)
        .flatMap(domainPort::removeDomain)
    );
  }

  @Override
  public Mono<Domain> addDomainRecord(DomainName domainName, Type recordType, String recordValue) throws RecordAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> domainPort.getDomainByName(domainName)
        .map(domain -> domain.addRecord(recordType, recordValue))
        .flatMap(domainPort::persistDomain)
    );
  }

  @Override
  public Mono<Domain> addDomainRecord(Long domainid, Type recordType, String recordValue) throws RecordAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> domainPort.getDomainById(domainid)
        .map(domain -> domain.addRecord(recordType, recordValue))
        .flatMap(domainPort::persistDomain)
    );
  }

  @Override
  public Mono<Domain> removeDomainRecord(DomainName domainName, Type type, String recordValue)
      throws DomainDoesNotExistsException, RecordDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> domainPort.getDomainByName(domainName)
        .map(domain -> domain.removeRecord(type, recordValue))
    );
  }

  @Override
  public Mono<Domain> removeDomainRecord(Long domainId, Type type, String recordValue)
      throws DomainDoesNotExistsException, RecordDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> domainPort.getDomainById(domainId)
        .map(domain -> domain.removeRecord(type, recordValue))
    );
  }

  private <T> Mono<T> isAdminUser(Supplier<Mono<T>> supplier) {
    return authorizationPort.isAdminUser()
        .flatMap(isAdminUser -> {
          if (isAdminUser) {
            return supplier.get();
          } else {
            return Mono.error(new UnauthorizedException());
          }
        })
        ;
  }
}
