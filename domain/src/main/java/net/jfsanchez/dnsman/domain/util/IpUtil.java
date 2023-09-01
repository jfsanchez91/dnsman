package net.jfsanchez.dnsman.domain.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.jfsanchez.dnsman.domain.error.InvalidIpv4AddressException;
import net.jfsanchez.dnsman.domain.error.InvalidIpv6AddressException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IpUtil {

  public static byte[] ipv4(String value) throws InvalidIpv4AddressException {
    try {
      return Inet4Address.getByName(value).getAddress();
    } catch (UnknownHostException e) {
      throw new InvalidIpv4AddressException(value);
    }
  }

  public static byte[] ipv6(String value) throws InvalidIpv6AddressException {
    try {
      return Inet6Address.getByName(value).getAddress();
    } catch (UnknownHostException e) {
      throw new InvalidIpv6AddressException(value);
    }
  }
}
