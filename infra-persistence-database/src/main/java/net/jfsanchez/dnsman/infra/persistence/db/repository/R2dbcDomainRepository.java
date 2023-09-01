package net.jfsanchez.dnsman.infra.persistence.db.repository;

import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import net.jfsanchez.dnsman.infra.persistence.db.entity.DomainEntity;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface R2dbcDomainRepository extends ReactorCrudRepository<DomainEntity, Long> {

  Mono<DomainEntity> findByName(String name);
}
