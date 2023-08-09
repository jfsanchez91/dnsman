package net.jfsanchez.dnsman.application.port.input;

import net.jfsanchez.dnsman.domain.entity.Domain;
import reactor.core.publisher.Flux;

public interface ListDomainsUseCase {
  Flux<Domain> listDomains();
}
