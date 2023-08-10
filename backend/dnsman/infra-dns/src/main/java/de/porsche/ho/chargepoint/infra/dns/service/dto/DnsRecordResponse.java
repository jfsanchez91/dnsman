package de.porsche.ho.chargepoint.infra.dns.service.dto;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import net.jfsanchez.dnsman.domain.valueobject.Record;

public record DnsRecordResponse(
    byte[] data
) {
  public static DnsRecordResponse fromDomain(Record record) {
    final var bytes = switch (record.type()) {
      case A -> ipv4(record.value());
      case AAAA -> ipv6(record.value());
      case CNAME -> record.value().getBytes();
      case MX -> throw new UnsupportedOperationException("Unsupported MX query operation.");
      case TXT -> throw new UnsupportedOperationException("Unsupported TXT query operation.");
    };
    return new DnsRecordResponse(bytes);
  }

  private static byte[] ipv4(String value) {
    try {
      return Inet4Address.getByName(value).getAddress();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  private static byte[] ipv6(String value) {
    try {
      return Inet6Address.getByName(value).getAddress();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }
}
