package net.jfsanchez.dnsman.infra.persistence.db.repository;

import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import net.jfsanchez.dnsman.infra.persistence.db.entity.RecordEntity;
import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface R2dbcRecordRepository extends ReactorCrudRepository<RecordEntity, Long> {

  Flux<RecordEntity> findAllByDomainId(Long domainId);
}
