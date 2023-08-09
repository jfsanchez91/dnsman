package net.jfsanchez.dnsman.application.port.input;

import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import reactor.core.publisher.Mono;

public interface GetDomain {
  Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException;

  Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException;
}
