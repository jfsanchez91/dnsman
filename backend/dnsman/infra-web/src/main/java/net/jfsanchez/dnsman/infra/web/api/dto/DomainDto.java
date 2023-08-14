package net.jfsanchez.dnsman.infra.web.api.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import net.jfsanchez.dnsman.domain.entity.Domain;

@Builder
@Serdeable.Serializable
public record DomainDto(
    Long id,
    String domainName,
    Set<String> records
) {
  public static DomainDto fromDomain(Domain entity) {
    return DomainDto.builder()
        .id(entity.id())
        .domainName(entity.domainName().value())
        .records(entity.records().stream().map(record -> "%s %s %s".formatted(record.type().name(), record.ttl(), record.value())).collect(Collectors.toSet()))
        .build();
  }
}
