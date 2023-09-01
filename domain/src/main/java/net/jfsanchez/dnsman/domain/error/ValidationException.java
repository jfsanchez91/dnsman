package net.jfsanchez.dnsman.domain.error;

public abstract class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
