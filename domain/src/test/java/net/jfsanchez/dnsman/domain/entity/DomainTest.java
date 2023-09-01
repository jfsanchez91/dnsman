package net.jfsanchez.dnsman.domain.entity;

import net.jfsanchez.dnsman.domain.error.RecordAlreadyExistsException;
import net.jfsanchez.dnsman.domain.error.RecordDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.Record;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DomainTest {
  @Test
  void addRecord_adds_a_new_dns_record() {
    final var domain = Domain.of("example.com");
    domain.addRecord(Type.A, "127.0.0.1", 0L);
    domain.addRecord(Type.A, "127.0.0.2", 10L);
    Assertions.assertEquals(2, domain.records().size());
  }

  @Test
  void addRecord_fails_when_record_already_exists() {
    final var domain = Domain.of("example.com");
    domain.addRecord(Type.A, "127.0.0.1", 0L);
    Assertions.assertThrows(RecordAlreadyExistsException.class, () -> domain.addRecord(Type.A, "127.0.0.1", 0L));
  }

  @Test
  void removeRecord_removes_a_dns_record() {
    final var domain = Domain.of("example.com");
    domain.addRecord(Type.A, "127.0.0.1", 0L);
    domain.addRecord(Type.A, "127.0.0.2", 0L);
    domain.removeRecord(Type.A, "127.0.0.1");
    Assertions.assertEquals(1, domain.records().size());
    Assertions.assertEquals(new Record(Type.A, "127.0.0.2", 0L), domain.records().iterator().next());
  }

  @Test
  void removeRecord_fails_when_record_does_not_exists() {
    final var domain = Domain.of("example.com");
    domain.addRecord(Type.A, "127.0.0.1", 0L);
    Assertions.assertThrows(RecordDoesNotExistsException.class, () -> domain.removeRecord(Type.A, "127.0.0.2"));
  }
}
