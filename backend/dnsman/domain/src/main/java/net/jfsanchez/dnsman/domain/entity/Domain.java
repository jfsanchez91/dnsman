package net.jfsanchez.dnsman.domain.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import net.jfsanchez.dnsman.domain.error.InvalidDomainNameException;
import net.jfsanchez.dnsman.domain.error.RecordAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.RecordDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Record;
import net.jfsanchez.dnsman.domain.valueobject.Type;

@Builder(toBuilder = true)
public record Domain(
    Long id,
    DomainName domainName,
    Set<Record> records
) {

  public Domain(DomainName name) {
    this(name, new HashSet<>());
  }

  public Domain(DomainName name, Set<Record> records) {
    this(null, name, records);
  }

  public Domain(Long id, DomainName domainName, Set<Record> records) {
    this.id = id;
    this.domainName = domainName;
    this.records = Objects.requireNonNullElseGet(records, HashSet::new);
    validate();
  }

  public static Domain of(String domainName) throws InvalidDomainNameException {
    return new Domain(new DomainName(domainName));
  }

  public static Domain of(String domainName, Set<Record> records) throws InvalidDomainNameException {
    return new Domain(new DomainName(domainName), records);
  }


  private void validate() {
  }

  public Domain addRecord(Type type, String value) throws RecordAlreadyExistsException {
    final var record = new Record(type, value);
    if (!records.add(record)) {
      throw new RecordAlreadyExistsException(this.domainName, type, value);
    }
    return this;
  }

  public Domain removeRecord(Type type, String value) throws RecordDoesNotExistsException {
    final var record = new Record(type, value);
    if (!records.remove(record)) {
      throw new RecordDoesNotExistsException(this.domainName, type, value);
    }
    return this;
  }

  public Set<Record> getRecordsByType(Type type) {
    return records.stream().filter(record -> record.type().equals(type)).collect(Collectors.toSet());
  }
}
