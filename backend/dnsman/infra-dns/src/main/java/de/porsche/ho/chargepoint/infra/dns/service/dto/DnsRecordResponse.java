package de.porsche.ho.chargepoint.infra.dns.service.dto;

import net.jfsanchez.dnsman.domain.util.IpUtil;
import net.jfsanchez.dnsman.domain.valueobject.Record;

public record DnsRecordResponse(
    byte[] data
) {
  public static DnsRecordResponse fromDomain(Record record) {
    final var bytes = switch (record.type()) {
      case A -> IpUtil.ipv4(record.value());
      case AAAA -> IpUtil.ipv6(record.value());
      case CNAME -> record.value().getBytes();
      case MX -> throw new UnsupportedOperationException("Unsupported MX query operation.");
      case TXT -> throw new UnsupportedOperationException("Unsupported TXT query operation.");
    };
    return new DnsRecordResponse(bytes);
  }
}
