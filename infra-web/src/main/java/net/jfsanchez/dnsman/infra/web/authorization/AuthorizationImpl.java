package net.jfsanchez.dnsman.infra.web.authorization;

import jakarta.inject.Singleton;
import net.jfsanchez.dnsman.application.port.output.AuthorizationPort;
import reactor.core.publisher.Mono;

@Singleton
public class AuthorizationImpl implements AuthorizationPort {
  @Override
  public Mono<Boolean> isAdminUser() {
    return Mono.just(true);
  }
}
