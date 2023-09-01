package net.jfsanchez.dnsman.infra.persistence.db.repository;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Join.Type;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import net.jfsanchez.dnsman.infra.persistence.db.entity.DomainEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Requires(notEnv = "in_memory")
@R2dbcRepository(dialect = Dialect.POSTGRES)
interface R2dbcDomainRepository extends ReactorCrudRepository<DomainEntity, Long> {

  @Override
  @Join(value = "records", type = Type.LEFT_FETCH)
  Mono<DomainEntity> findById(Long id);

  @Join(value = "records", type = Type.LEFT_FETCH)
  Mono<DomainEntity> findByName(String name);

  @Override
  @Join(value = "records", type = Type.LEFT_FETCH)
  Flux<DomainEntity> findAll();

  @NonNull
  @Override
  @Join(value = "records", type = Type.LEFT_FETCH)
  <T extends DomainEntity> Mono<T> save(@NonNull T entity);

  @NonNull
  @Override
  @Join(value = "records", type = Type.LEFT_FETCH)
  <T extends DomainEntity> Mono<T> update(@NonNull T entity);

  @NonNull
//  @Join("records")
  Mono<Long> deleteById(@NonNull Long id);
}
