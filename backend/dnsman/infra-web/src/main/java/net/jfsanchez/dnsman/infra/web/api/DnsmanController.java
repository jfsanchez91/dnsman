package net.jfsanchez.dnsman.infra.web.api;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/dnsman")
public class DnsmanController {

  @Get(uri = "/", produces = "text/plain")
  public String index() {
    return "Example Response";
  }
}
