package net.jfsanchez.dnsman.infra.web.error.handler;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable.Serializable
record ErrorDto(
    String message
) {

  public static ErrorDto of(String message) {
    return new ErrorDto(message);
  }
}
