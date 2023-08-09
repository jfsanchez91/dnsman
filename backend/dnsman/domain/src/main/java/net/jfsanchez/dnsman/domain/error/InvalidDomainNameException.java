package net.jfsanchez.dnsman.domain.error;

public class InvalidDomainNameException extends ValidationException {
  public final String domainName;

  public InvalidDomainNameException(String domainName) {
    super("Invalid domain name (name='%s').".formatted(domainName));
    this.domainName = domainName;
  }
}
