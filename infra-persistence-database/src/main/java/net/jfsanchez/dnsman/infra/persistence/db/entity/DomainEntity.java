package net.jfsanchez.dnsman.infra.persistence.db.entity;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Record;


@Setter
@Getter
@Builder
@Serdeable
@MappedEntity("domain")
public class DomainEntity {

  @Id
  @GeneratedValue(GeneratedValue.Type.AUTO)
  private Long id;

  @Column
  @NotBlank
  private String name;

  @Column
  @Nullable
  @OneToMany(mappedBy = "domain", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("ttl desc")
  private Set<RecordEntity> records = new HashSet<>();

  public Domain toDomain() {
    final Set<Record> records;
    if (this.records != null) {
      records = this.records.stream().map(RecordEntity::toDomain).collect(Collectors.toSet());
    } else {
      records = null;
    }
    return Domain.builder()
        .id(this.id)
        .domainName(DomainName.of(this.name))
        .records(records)
        .build();
  }

  public static DomainEntity from(Domain domain) {
    final var entity = DomainEntity.builder()
        .id(domain.id())
        .name(domain.domainName().value())
        .build();
    final Set<RecordEntity> records;
    if (CollectionUtils.isEmpty(domain.records())) {
      records = null;
    } else {
      records = domain.records().stream().map(it -> {
        final var record = RecordEntity.from(it);
        record.setDomain(entity);
        return record;
      }).collect(Collectors.toSet());
    }
    entity.setRecords(records);
    return entity;
  }
}
