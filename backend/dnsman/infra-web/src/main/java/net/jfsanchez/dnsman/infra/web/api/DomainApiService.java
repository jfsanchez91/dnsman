package net.jfsanchez.dnsman.infra.web.api;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jfsanchez.dnsman.application.service.DomainService;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import net.jfsanchez.dnsman.infra.web.api.dto.AddDomainRecordDto;
import net.jfsanchez.dnsman.infra.web.api.dto.DomainDto;
import net.jfsanchez.dnsman.infra.web.api.dto.RegisterDomainDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class DomainApiService {
  private final DomainService domainService;

  public Flux<DomainDto> listDomains() {
    return domainService.listDomains()
        .map(DomainDto::fromDomain)
        ;
  }

  public Mono<DomainDto> registerDomain(RegisterDomainDto dto) {
    return domainService.registerDomain(DomainName.of(dto.domainName()))
        .map(DomainDto::fromDomain)
        ;
  }

  public Mono<DomainDto> addDomainRecord(Long domainId, AddDomainRecordDto dto) {
    return domainService.addDomainRecord(domainId, Type.valueOf(dto.type().name()), dto.value())
        .map(DomainDto::fromDomain)
        ;
  }
}
