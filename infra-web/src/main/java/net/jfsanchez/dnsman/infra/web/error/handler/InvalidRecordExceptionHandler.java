package net.jfsanchez.dnsman.infra.web.error.handler;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import net.jfsanchez.dnsman.domain.error.InvalidRecordException;

@Produces
@Singleton
@Requires(classes = {InvalidRecordException.class, ExceptionHandler.class})
class InvalidRecordExceptionHandler implements ExceptionHandler<InvalidRecordException, HttpResponse> {

  @Override
  public HttpResponse handle(HttpRequest request, InvalidRecordException exception) {
    return HttpResponse.badRequest(ErrorDto.of(exception.getMessage()));
  }
}
