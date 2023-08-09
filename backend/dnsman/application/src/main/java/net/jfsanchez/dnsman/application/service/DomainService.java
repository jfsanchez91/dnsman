package net.jfsanchez.dnsman.application.service;

import jakarta.inject.Singleton;
import java.util.function.Supplier;
import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.application.port.input.AddDomainRecordUseCase;
import net.jfsanchez.dnsman.application.port.input.GetDomain;
import net.jfsanchez.dnsman.application.port.input.ListDomainsUseCase;
import net.jfsanchez.dnsman.application.port.input.RegisterDomainUseCase;
import net.jfsanchez.dnsman.application.port.input.RemoveDomainRecordUseCase;
import net.jfsanchez.dnsman.application.port.input.RemoveDomainUseCase;
import net.jfsanchez.dnsman.application.port.output.AuthorizationPort;
import net.jfsanchez.dnsman.application.port.output.GetDomainPort;
import net.jfsanchez.dnsman.application.port.output.GetDomainsPort;
import net.jfsanchez.dnsman.application.port.output.PersistDomainPort;
import net.jfsanchez.dnsman.application.port.output.RemoveDomainPort;
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
public class DomainService implements RegisterDomainUseCase, GetDomain, RemoveDomainUseCase, ListDomainsUseCase, AddDomainRecordUseCase,
    RemoveDomainRecordUseCase {
  private final GetDomainPort getDomainPort;
  private final GetDomainsPort getDomainsPort;
  private final PersistDomainPort persistDomainPort;
  private final RemoveDomainPort removeDomainPort;
  private final AuthorizationPort authorizationPort;

  public DomainService(
      GetDomainPort getDomainPort,
      GetDomainsPort getDomainsPort,
      PersistDomainPort persistDomainPort,
      RemoveDomainPort removeDomainPort,
      AuthorizationPort authorizationPort
  ) {
    this.getDomainPort = getDomainPort;
    this.getDomainsPort = getDomainsPort;
    this.persistDomainPort = persistDomainPort;
    this.removeDomainPort = removeDomainPort;
    this.authorizationPort = authorizationPort;
  }

  @Override
  public Mono<Domain> registerDomain(DomainName domainName) throws DomainAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> persistDomainPort.persistDomain(new Domain(domainName)));
  }

  @Override
  public Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException {
    return getDomainPort.getDomainByName(domainName);
  }

  @Override
  public Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException {
    return getDomainPort.getDomainById(domainId);
  }

  @Override
  public Flux<Domain> listDomains() {
    return getDomainsPort.getDomains();
  }

  @Override
  public Mono<Domain> removeDomainByName(DomainName domainName) throws DomainDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> getDomainPort.getDomainByName(domainName)
        .flatMap(removeDomainPort::removeDomain)
    );
  }

  @Override
  public Mono<Domain> removeDomainById(Long domainId) throws DomainDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> getDomainPort.getDomainById(domainId)
        .flatMap(removeDomainPort::removeDomain)
    );
  }

  @Override
  public Mono<Domain> addDomainRecord(DomainName domainName, Type recordType, String recordValue) throws RecordAlreadyExistsException, UnauthorizedException {
    return isAdminUser(() -> getDomainPort.getDomainByName(domainName)
        .map(domain -> domain.addRecord(recordType, recordValue))
        .flatMap(persistDomainPort::persistDomain)
    );
  }

  @Override
  public Mono<Domain> removeDnsRecord(DomainName domainName, Type type, String recordValue)
      throws DomainDoesNotExistsException, RecordDoesNotExistsException, UnauthorizedException {
    return isAdminUser(() -> getDomainPort.getDomainByName(domainName)
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
