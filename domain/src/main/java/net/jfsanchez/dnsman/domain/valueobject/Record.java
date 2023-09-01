package net.jfsanchez.dnsman.domain.valueobject;

import static net.jfsanchez.dnsman.domain.util.AssertionUtil._assert;

import lombok.Builder;
import net.jfsanchez.dnsman.domain.error.InvalidRecordException;
import net.jfsanchez.dnsman.domain.util.IpUtil;

@Builder
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
    _assert(value != null, "Record value can't be null");
    _assert(!value.isBlank(), "Record value can't be empty");
    _assert(ttl != null, "Record TTL can't be null");
    _assert(ttl >= 0, "Record TTL must be greater or equal to zero"); // zero means no-cache
    switch (type) {
      case A -> {
        _assert(isValidIpv4(value), "A records must be a valid IPv4 address");
      }
      case AAAA -> {
        _assert(isValidIpv6(value), "AAAA records must be a valid IPv6 address");
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
