package net.jfsanchez.dnsman.application.error;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException() {
    super("User is not authorized.");
  }
}
