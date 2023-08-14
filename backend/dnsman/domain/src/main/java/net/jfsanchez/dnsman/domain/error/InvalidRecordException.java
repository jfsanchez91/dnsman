package net.jfsanchez.dnsman.domain.error;

import net.jfsanchez.dnsman.domain.valueobject.Type;

public class InvalidRecordException extends ValidationException {
  public final Type type;
  public final String value;
  public final Long ttl;

  public InvalidRecordException(Type type, String value, Long ttl) {
    super("Invalid domain record (type='%s', value='%s', ttl='%s').".formatted(type, value, ttl));
    this.type = type;
    this.value = value;
    this.ttl = ttl;
  }
}
