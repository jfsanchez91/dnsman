package net.jfsanchez.dnsman.application.port.output;

import java.util.function.Supplier;

public interface TransactionManagementPort {
  <T> T withTransaction(Supplier<T> supplier);

  <T> void withTransaction(Runnable runnable);
}
