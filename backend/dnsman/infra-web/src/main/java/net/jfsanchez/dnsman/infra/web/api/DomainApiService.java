package net.jfsanchez.dnsman.infra.web.api;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jfsanchez.dnsman.application.service.DomainService;
import net.jfsanchez.dnsman.infra.web.api.dto.DomainDto;
import reactor.core.publisher.Flux;

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
}
