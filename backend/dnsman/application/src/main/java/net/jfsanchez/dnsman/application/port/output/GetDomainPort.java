package net.jfsanchez.dnsman.application.port.output;

import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import reactor.core.publisher.Mono;

public interface GetDomainPort {
  Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException;

  Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException;
}
