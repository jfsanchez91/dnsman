package de.porsche.ho.chargepoint.infra.dns.service;

import de.porsche.ho.chargepoint.infra.dns.service.dto.DnsRecordResponse;
import io.netty.handler.codec.dns.DnsRecordType;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jfsanchez.dnsman.application.service.DomainService;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class DnsApiService {
  private final DomainService domainService;

  public Flux<DnsRecordResponse> resolveQuery(DnsRecordType type, String domainName) {
    return domainService.getDomainByName(DomainName.of(domainName(domainName)))
        .flatMapIterable(domain -> domain.getRecordsByType(recordType(type)))
        .map(DnsRecordResponse::fromDomain)
        .onErrorResume(DomainDoesNotExistsException.class, ignored -> Mono.empty())
        ;
  }

  private static Type recordType(DnsRecordType type) {
    return Type.valueOf(type.name());
  }

  private static String domainName(String value) {
    if (value.endsWith(".")) {
      return value.substring(0, value.length() - 1);
    }
    return value;
  }
}
