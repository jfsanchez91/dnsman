package net.jfsanchez.dnsman.domain.error;

public class InvalidIpv4AddressException extends ValidationException {

  public final String address;

  public InvalidIpv4AddressException(String address) {
    super("Invalid IPv4 address (address='%s')".formatted(address));
    this.address = address;
  }
}
