package net.jfsanchez.dnsman.infra.web.api.dto;

import lombok.Builder;
import net.jfsanchez.dnsman.domain.entity.Domain;

@Builder
public record DomainDto(
    String domainName
) {
  public static DomainDto fromDomain(Domain entity) {
    return DomainDto.builder()
        .domainName(entity.domainName().value())
        .build();
  }
}
