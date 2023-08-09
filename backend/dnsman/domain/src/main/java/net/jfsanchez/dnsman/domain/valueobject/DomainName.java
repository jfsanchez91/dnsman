package net.jfsanchez.dnsman.domain.valueobject;

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
    assert value != null;
    assert !value.isBlank();
    try {
      final var host = URI.create("https://" + value).toURL().getHost();
      assert host != null;
      assert !host.isBlank();
    } catch (MalformedURLException | IllegalArgumentException e) {
      throw new AssertionError();
    }
  }
}
