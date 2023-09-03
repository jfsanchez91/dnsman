package net.jfsanchez.dnsman.infra.persistence.db.transaction;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.transaction.reactive.ReactiveTransactionStatus;
import io.r2dbc.spi.Connection;
import jakarta.inject.Singleton;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import net.jfsanchez.dnsman.application.port.output.TransactionManagementPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@RequiredArgsConstructor
@Requires(notEnv = "in_memory")
class R2dbcTransactionManagementAdapter implements TransactionManagementPort {

  private final R2dbcOperations operations;

  @NonNull
  @Override
  public <T> Mono<T> withTransaction(Supplier<Mono<T>> supplier) {
    return Flux.from(operations.withTransaction((ReactiveTransactionStatus<Connection> status) -> supplier.get())).last()
        .onErrorResume(NoSuchElementException.class, error -> Mono.empty())
        ;
  }
}
