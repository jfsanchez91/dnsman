package net.jfsanchez.dnsman.application.port.output;

import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public interface TransactionManagementPort {

  <T> Mono<T> withTransaction(Supplier<Mono<T>> supplier);
}
