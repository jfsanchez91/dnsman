package net.jfsanchez.dnsman.domain.error;

import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;

public class RecordAlreadyExistsException extends ValidationException {
  public final DomainName domainName;
  public final Type type;
  public final String value;

  public RecordAlreadyExistsException(DomainName domainName, Type type, String value) {
    super("DNS Record with type='%s' and value='%s' already exists for the domain='%s'.".formatted(domainName.value(), type, value));
    this.domainName = domainName;
    this.type = type;
    this.value = value;
  }
}
