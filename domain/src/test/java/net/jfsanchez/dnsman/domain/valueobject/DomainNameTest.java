package net.jfsanchez.dnsman.domain.valueobject;

import net.jfsanchez.dnsman.domain.error.InvalidDomainNameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DomainNameTest {
  @Test
  void it_fails_to_validate_wrong_domain_names() {
    Assertions.assertThrows(InvalidDomainNameException.class, () -> DomainName.of(null));
    Assertions.assertThrows(InvalidDomainNameException.class, () -> DomainName.of(""));
    Assertions.assertThrows(InvalidDomainNameException.class, () -> DomainName.of("  "));
    Assertions.assertThrows(InvalidDomainNameException.class, () -> DomainName.of("abc@@%%**()-+_+!~`def"));
  }
}
