package net.jfsanchez.dnsman.application.port.input;

import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import reactor.core.publisher.Mono;

public interface RemoveDomainUseCase {
  Mono<Domain> removeDomainByName(DomainName domainName) throws DomainDoesNotExistsException, UnauthorizedException;

  Mono<Domain> removeDomainById(Long domainId) throws DomainDoesNotExistsException, UnauthorizedException;
}
