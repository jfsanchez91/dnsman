package net.jfsanchez.dnsman.domain.error;

import net.jfsanchez.dnsman.domain.valueobject.DomainName;

public class DomainAlreadyExistsException extends ValidationException {
  public final DomainName domainName;

  public DomainAlreadyExistsException(DomainName domainName) {
    super("Domain with name='%s' already exists.".formatted(domainName.value()));
    this.domainName = domainName;
  }
}
