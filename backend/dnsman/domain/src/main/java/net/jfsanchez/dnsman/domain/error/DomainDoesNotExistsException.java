package net.jfsanchez.dnsman.domain.error;

import net.jfsanchez.dnsman.domain.valueobject.DomainName;

public class DomainDoesNotExistsException extends ValidationException {
  public final DomainName domainName;
  public final Long domainId;

  public DomainDoesNotExistsException(DomainName domainName) {
    super("Domain with name='%s' does not exists.".formatted(domainName.value()));
    this.domainName = domainName;
    this.domainId = null;
  }

  public DomainDoesNotExistsException(Long domainId) {
    super("Domain with Id='%s' does not exists.".formatted(domainId));
    this.domainId = domainId;
    this.domainName = null;
  }
}
