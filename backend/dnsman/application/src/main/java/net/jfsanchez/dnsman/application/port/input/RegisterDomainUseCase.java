package net.jfsanchez.dnsman.application.port.input;

import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import reactor.core.publisher.Mono;

public interface RegisterDomainUseCase {
  Mono<Domain> registerDomain(DomainName domainName) throws DomainAlreadyExistsException, UnauthorizedException;
}
