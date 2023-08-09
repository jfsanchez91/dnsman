package net.jfsanchez.dnsman.application.port.output;

import reactor.core.publisher.Mono;

public interface AuthorizationPort {
  Mono<Boolean> isAdminUser();
}
