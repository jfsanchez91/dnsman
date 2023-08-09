package net.jfsanchez.dnsman.infra.web.api;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.jfsanchez.dnsman.infra.web.api.dto.DomainDto;
import reactor.core.publisher.Flux;

@Controller(DomainController.Paths.ROOT_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class DomainController {
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  static class Paths {
    static final String ROOT_PATH = "/api/domain";
    static final String LIST_DOMAINS = "/";
  }

  private final DomainApiService domainApiService;

  @Get(uri = Paths.LIST_DOMAINS)
  public Flux<DomainDto> listDomains() {
    return domainApiService.listDomains();
  }
}
