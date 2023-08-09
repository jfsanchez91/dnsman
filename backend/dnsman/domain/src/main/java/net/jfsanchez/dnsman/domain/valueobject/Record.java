package net.jfsanchez.dnsman.domain.valueobject;

public record Record(
    Type type,
    String value
) {
  public Record(Type type, String value) {
    this.type = type;
    this.value = value;

    validate();
  }

  public void validate() {
    // TODO: validate A records to be valid IPv4 addresses
    // TODO: validate AAAA records to be valid IPv6 addresses
  }
}
