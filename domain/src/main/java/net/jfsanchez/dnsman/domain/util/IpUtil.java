package net.jfsanchez.dnsman.domain.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IpUtil {
  public static byte[] ipv4(String value) {
    try {
      return Inet4Address.getByName(value).getAddress();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] ipv6(String value) {
    try {
      return Inet6Address.getByName(value).getAddress();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }
}
