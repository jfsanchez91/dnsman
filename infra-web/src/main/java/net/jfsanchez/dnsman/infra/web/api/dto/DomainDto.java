package net.jfsanchez.dnsman.infra.web.api.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.Set;
import lombok.Builder;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.valueobject.Record;

@Builder
@Serdeable.Serializable
public record DomainDto(
    Long id,
    String domainName,
    Set<Record> records
) {

  public static DomainDto fromDomain(Domain entity) {
    return DomainDto.builder()
        .id(entity.id())
        .domainName(entity.domainName().value())
        .records(entity.records())
        .build();
  }
}
