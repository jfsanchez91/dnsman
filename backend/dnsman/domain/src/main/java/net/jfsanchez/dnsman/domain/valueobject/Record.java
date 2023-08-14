package net.jfsanchez.dnsman.domain.valueobject;

import net.jfsanchez.dnsman.domain.error.InvalidRecordException;
import net.jfsanchez.dnsman.domain.util.IpUtil;

public record Record(
    Type type,
    String value,
    Long ttl
) {
  public Record(Type type, String value) {
    this(type, value, 0L);
  }

  public Record(Type type, String value, Long ttl) {
    this.type = type;
    this.value = value;
    this.ttl = ttl;
    try {
      validate();
    } catch (AssertionError e) {
      throw new InvalidRecordException(type, value, ttl);
    }
  }

  private void validate() {
    assert value != null;
    assert !value.isBlank();
    assert ttl != null;
    assert ttl >= 0; // zero means no-cache
    switch (type) {
      case A -> {
        assert isValidIpv4(value);
      }
      case AAAA -> {
        assert isValidIpv6(value);
      }
      case CNAME -> DomainName.of(value);
    }
    // TODO: Validate MX records
  }

  private boolean isValidIpv4(String value) {
    return IpUtil.ipv4(value).length == 4;
  }

  private boolean isValidIpv6(String value) {
    return IpUtil.ipv6(value).length == 6;
  }
}
