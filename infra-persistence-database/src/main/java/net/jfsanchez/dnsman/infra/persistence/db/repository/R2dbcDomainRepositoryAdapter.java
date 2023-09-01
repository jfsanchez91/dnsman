package net.jfsanchez.dnsman.infra.persistence.db.repository;

import io.r2dbc.spi.R2dbcException;
import jakarta.inject.Singleton;
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

  private final R2dbcDomainRepository repository;

  @Override
  public Mono<Domain> getDomainById(Long domainId) throws DomainDoesNotExistsException {
    return repository.findById(domainId).map(DomainEntity::toDomain);
  }

  @Override
  public Mono<Domain> getDomainByName(DomainName domainName) throws DomainDoesNotExistsException {
    return repository.findByName(domainName.value()).map(DomainEntity::toDomain);
  }

  @Override
  public Flux<Domain> listDomains() {
    return repository.findAll().map(DomainEntity::toDomain);
  }

  @Override
  public Mono<Domain> persistDomain(Domain domain) {
    return repository.save(DomainEntity.from(domain)).map(DomainEntity::toDomain).onErrorMap(R2dbcException.class, error -> {
      if (error.getErrorCode() == DUPLICATED_KEY_ERROR_CODE || Objects.equals(error.getSqlState(), "" + DUPLICATED_KEY_ERROR_CODE)) {
        return new DomainAlreadyExistsException(domain.domainName());
      }
      return error;
    });
  }

  @Override
  public Mono<Domain> updateDomain(Domain domain) {
    return repository.update(DomainEntity.from(domain)).map(DomainEntity::toDomain);
  }

  @Override
  public Mono<Domain> removeDomain(Domain domain) {
    return repository.deleteById(domain.id()).thenReturn(domain);
  }
}
