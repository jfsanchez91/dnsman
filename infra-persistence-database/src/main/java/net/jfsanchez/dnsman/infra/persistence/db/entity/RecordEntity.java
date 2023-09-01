package net.jfsanchez.dnsman.infra.persistence.db.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.Relation.Cascade;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.jfsanchez.dnsman.domain.valueobject.Record;
import net.jfsanchez.dnsman.domain.valueobject.Type;


@Setter
@Getter
@Builder
@Serdeable
@MappedEntity("record")
public class RecordEntity {

  @Id
  @GeneratedValue(GeneratedValue.Type.AUTO)
  private Long id;

  @NotBlank
  @Column
  private String value;

  @Min(0)
  @Column
  private Long ttl;

  @Enumerated(EnumType.STRING)
  private Type type;

  @Relation(value = Relation.Kind.MANY_TO_ONE, cascade = Cascade.ALL)
  private DomainEntity domain;


  public Record toDomain() {
    return Record.builder()
        .value(this.value)
        .ttl(this.ttl)
        .type(this.type)
        .build();
  }

  public static RecordEntity from(Record record) {
    return RecordEntity.builder()
        .value(record.value())
        .ttl(record.ttl())
        .type(record.type())
        .build();
  }
}
