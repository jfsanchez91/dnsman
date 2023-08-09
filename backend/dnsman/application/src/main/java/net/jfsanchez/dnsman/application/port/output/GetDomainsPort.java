package net.jfsanchez.dnsman.application.port.output;

import net.jfsanchez.dnsman.domain.entity.Domain;
import reactor.core.publisher.Flux;

public interface GetDomainsPort {
  Flux<Domain> getDomains();
}
