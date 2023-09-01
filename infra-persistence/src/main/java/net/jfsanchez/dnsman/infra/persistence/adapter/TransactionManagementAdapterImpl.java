package net.jfsanchez.dnsman.infra.persistence.adapter;

import jakarta.inject.Singleton;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jfsanchez.dnsman.application.port.output.TransactionManagementPort;

@Slf4j
@Singleton
@RequiredArgsConstructor
class TransactionManagementAdapterImpl implements TransactionManagementPort {
  private final Semaphore semaphore = new Semaphore(1);

  @Override
  public <T> T withTransaction(Supplier<T> supplier) {
    acquire();
    final var result = supplier.get();
    release();
    return result;
  }

  @Override
  public <T> void withTransaction(Runnable runnable) {
    acquire();
    runnable.run();
    release();
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
