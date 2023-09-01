package net.jfsanchez.dnsman.infra.persistence.inmemory.transaction;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jfsanchez.dnsman.application.port.output.TransactionManagementPort;
import reactor.core.publisher.Mono;

@Slf4j
@Singleton
@RequiredArgsConstructor
@Requires(env = "in_memory")
class InMemoryTransactionManagementAdapter implements TransactionManagementPort {

  private final Semaphore semaphore = new Semaphore(1);

  @Override
  public <T> Mono<T> withTransaction(Supplier<Mono<T>> supplier) {
    acquire();
    final var result = supplier.get();
    release();
    return result;
  }

  private void acquire() {
    try {
      semaphore.acquire();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void release() {
    semaphore.release();
  }
}
