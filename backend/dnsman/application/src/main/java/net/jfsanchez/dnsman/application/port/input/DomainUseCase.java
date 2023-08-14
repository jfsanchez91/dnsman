package net.jfsanchez.dnsman.application.port.input;

import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.error.RecordAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.RecordDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainUseCase {
  Mono<Domain> registerDomain(DomainName domainName) throws DomainAlreadyExistsException, UnauthorizedException;

  Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException;

  Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException;

  Mono<Domain> removeDomainByName(DomainName domainName) throws DomainDoesNotExistsException, UnauthorizedException;

  Mono<Domain> removeDomainById(Long domainId) throws DomainDoesNotExistsException, UnauthorizedException;

  Flux<Domain> listDomains();

  Mono<Domain> addDomainRecord(DomainName domainName, Type recordType, String recordValue, Long ttl)
      throws RecordAlreadyExistsException, UnauthorizedException;

  Mono<Domain> addDomainRecord(Long domainId, Type recordType, String recordValue, Long ttl)
      throws RecordAlreadyExistsException, UnauthorizedException;

  Mono<Domain> removeDomainRecord(DomainName domainName, Type type, String recordValue)
      throws DomainDoesNotExistsException, RecordDoesNotExistsException,
      UnauthorizedException;

  Mono<Domain> removeDomainRecord(Long domainId, Type type, String recordValue) throws DomainDoesNotExistsException, RecordDoesNotExistsException,
      UnauthorizedException;
}
