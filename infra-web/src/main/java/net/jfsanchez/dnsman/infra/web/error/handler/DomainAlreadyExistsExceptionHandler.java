package net.jfsanchez.dnsman.infra.web.error.handler;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;

@Produces
@Singleton
@Requires(classes = {DomainAlreadyExistsException.class, ExceptionHandler.class})
class DomainAlreadyExistsExceptionHandler implements ExceptionHandler<DomainAlreadyExistsException, HttpResponse> {

  @Override
  public HttpResponse handle(HttpRequest request, DomainAlreadyExistsException exception) {
    return HttpResponse.badRequest(ErrorDto.of("Domain already exists"));
  }
}
