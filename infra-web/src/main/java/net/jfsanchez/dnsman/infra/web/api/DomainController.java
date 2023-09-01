package net.jfsanchez.dnsman.infra.web.api;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.jfsanchez.dnsman.infra.web.api.dto.AddDomainRecordDto;
import net.jfsanchez.dnsman.infra.web.api.dto.DomainDto;
import net.jfsanchez.dnsman.infra.web.api.dto.RegisterDomainDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@Controller(DomainController.Paths.ROOT_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class DomainController {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  static class Paths {

    static final String ROOT_PATH = "/api/domain";
    static final String ADD_DOMAIN_RECORD = "/{domainId}/";
  }

  private final DomainApiService domainApiService;

  @Get()
  public Flux<DomainDto> listDomains() {
    return domainApiService.listDomains();
  }

  @Post(consumes = MediaType.APPLICATION_JSON)
  public Mono<DomainDto> registerDomain(@Body @Valid RegisterDomainDto dto) {
    return domainApiService.registerDomain(dto);
  }

  @Post(uri = Paths.ADD_DOMAIN_RECORD, consumes = MediaType.APPLICATION_JSON)
  public Mono<DomainDto> addDomainRecord(
      @PathVariable("domainId") Long domainId,
      @Body @Valid AddDomainRecordDto dto
  ) {
    return domainApiService.addDomainRecord(domainId, dto);
  }
}
