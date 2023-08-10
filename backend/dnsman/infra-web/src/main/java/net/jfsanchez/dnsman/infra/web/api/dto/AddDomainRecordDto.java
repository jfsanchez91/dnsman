package net.jfsanchez.dnsman.infra.web.api.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Builder
@Serdeable.Deserializable
public record AddDomainRecordDto(
    RecordType type,
    String value
) {
}
