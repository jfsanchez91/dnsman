package net.jfsanchez.dnsman.domain.error;

import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;

public class RecordDoesNotExistsException extends ValidationException {
  public final DomainName domainName;
  public final Type type;
  public final String value;

  public RecordDoesNotExistsException(DomainName domainName, Type type, String value) {
    super("DNS Record with type='%s' and value='%s' does not exists for the domain='%s'.".formatted(domainName.value(), type, value));
    this.domainName = domainName;
    this.type = type;
    this.value = value;
  }
}
