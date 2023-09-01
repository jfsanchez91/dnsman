package net.jfsanchez.dnsman.domain.error;

public class InvalidIpv6AddressException extends ValidationException {

  public final String address;

  public InvalidIpv6AddressException(String address) {
    super("Invalid IPv6 address (address='%s')".formatted(address));
    this.address = address;
  }
}
