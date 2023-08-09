package net.jfsanchez.dnsman.application.port.output;

import net.jfsanchez.dnsman.domain.entity.Domain;
import reactor.core.publisher.Mono;

public interface PersistDomainPort {
  Mono<Domain> persistDomain(Domain domain);
}
