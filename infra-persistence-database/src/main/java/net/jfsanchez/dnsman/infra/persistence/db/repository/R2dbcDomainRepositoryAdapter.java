package net.jfsanchez.dnsman.infra.persistence.db.repository;

import io.r2dbc.spi.R2dbcException;
import jakarta.inject.Singleton;
import java.util.HashSet;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jfsanchez.dnsman.application.port.output.DomainPort;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.infra.persistence.db.entity.DomainEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Singleton
@RequiredArgsConstructor
class R2dbcDomainRepositoryAdapter implements DomainPort {

  private static final int DUPLICATED_KEY_ERROR_CODE = 23505;

  private final R2dbcDomainRepository domainRepository;
  private final R2dbcRecordRepository recordRepository;

  @Override
  public Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException {
    return domainRepository.findById(domainId).flatMap(this::fetchRecords).map(DomainEntity::toDomain);
  }

  @Override
  public Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException {
    return domainRepository.findByName(domainName.value()).flatMap(this::fetchRecords).map(DomainEntity::toDomain);
  }

  @Override
  public Flux<Domain> listDomains() {
    return domainRepository.findAll().flatMap(this::fetchRecords).map(DomainEntity::toDomain);
  }

  @Override
  public Mono<Domain> persistDomain(Domain domain) {
    return domainRepository.save(DomainEntity.from(domain)).flatMap(this::fetchRecords)
        .map(DomainEntity::toDomain).onErrorMap(R2dbcException.class, error -> {
          if (error.getErrorCode() == DUPLICATED_KEY_ERROR_CODE || Objects.equals(error.getSqlState(), "" + DUPLICATED_KEY_ERROR_CODE)) {
            return new DomainAlreadyExistsException(domain.domainName());
          }
          return error;
        });
  }

  @Override
  public Mono<Domain> updateDomain(Domain domain) {
    return domainRepository.update(DomainEntity.from(domain)).flatMap(this::fetchRecords).map(DomainEntity::toDomain);
  }

  @Override
  public Mono<Domain> removeDomain(Domain domain) {
    return domainRepository.deleteById(domain.id()).thenReturn(domain);
  }

  private Mono<DomainEntity> fetchRecords(DomainEntity domainEntity) {
    return recordRepository.findAllByDomainId(domainEntity.getId())
        .collectList().doOnNext(records -> domainEntity.setRecords(new HashSet<>(records))).thenReturn(domainEntity);
  }
}
