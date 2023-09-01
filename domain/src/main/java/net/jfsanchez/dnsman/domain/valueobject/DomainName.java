package net.jfsanchez.dnsman.domain.valueobject;

import static net.jfsanchez.dnsman.domain.util.AssertionUtil._assert;

import java.net.MalformedURLException;
import java.net.URI;
import net.jfsanchez.dnsman.domain.error.InvalidDomainNameException;

public record DomainName(
    String value
) {

  public DomainName(String value) {
    this.value = value;
    try {
      validate();
    } catch (AssertionError e) {
      throw new InvalidDomainNameException(value);
    }
  }

  public static DomainName of(String value) {
    return new DomainName(value);
  }

  private void validate() {
    _assert(value != null, "Domain name must not be null");
    _assert(!value.isBlank(), "Domain name must not be blank");
    try {
      final var host = URI.create("https://" + value).toURL().getHost();
      _assert(host != null && !host.isBlank(), "Domain name is invalid");
    } catch (MalformedURLException | IllegalArgumentException e) {
      throw new AssertionError();
    }
  }
}
