package net.jfsanchez.dnsman.infra.persistence.inmemory.repository;

import io.micronaut.context.annotation.Requires;
import jakarta.annotation.Nullable;
import jakarta.inject.Singleton;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import net.jfsanchez.dnsman.application.port.output.DomainPort;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@Requires(env = "in_memory")
public class InMemoryDomainRepositoryAdapter implements DomainPort {

  private static final ConcurrentHashMap<Long, Domain> DOMAINS_BY_ID = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<String, Domain> DOMAINS_BY_NAME = new ConcurrentHashMap<>();
  private static final AtomicLong COUNTER = new AtomicLong(0);

  @Override
  public Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException {
    return nullableMono(DOMAINS_BY_ID.get(domainId), () -> new DomainDoesNotExistsException(domainId));
  }

  @Override
  public Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException {
    return nullableMono(DOMAINS_BY_NAME.get(domainName.value()), () -> new DomainDoesNotExistsException(domainName));
  }

  private static <T> Mono<T> nullableMono(@Nullable T value, Supplier<Throwable> error) {
    return Mono.create(sink -> {
      if (value == null) {
        sink.error(error.get());
      } else {
        sink.success(value);
      }
    });
  }

  @Override
  public Flux<Domain> listDomains() {
    return Flux.fromIterable(DOMAINS_BY_ID.values());
  }

  @Override
  public Mono<Domain> persistDomain(Domain domain) {
    if (domain.id() == null && DOMAINS_BY_NAME.containsKey(domain.domainName().value())) {
      return Mono.error(new DomainAlreadyExistsException(domain.domainName()));
    }
    final var id = Objects.requireNonNullElse(domain.id(), COUNTER.getAndIncrement());
    final var storedDomain = domain.toBuilder().id(id).build();
    DOMAINS_BY_ID.put(id, storedDomain);
    DOMAINS_BY_NAME.put(storedDomain.domainName().value(), storedDomain);
    return Mono.just(storedDomain);
  }

  @Override
  public Mono<Domain> updateDomain(Domain domain) {
    return persistDomain(domain);
  }

  @Override
  public Mono<Domain> removeDomain(Domain domain) {
    DOMAINS_BY_ID.remove(domain.id());
    DOMAINS_BY_NAME.remove(domain.domainName().value());
    return Mono.just(domain);
  }
}
