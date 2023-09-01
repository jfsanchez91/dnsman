package net.jfsanchez.dnsman.domain.valueobject;

import net.jfsanchez.dnsman.domain.error.InvalidDomainNameException;
import net.jfsanchez.dnsman.domain.error.InvalidRecordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RecordTest {

  @Test
  void it_fails_to_validate_wrong_records() {
    Assertions.assertThrows(InvalidRecordException.class, () -> Record.builder().build());
    Assertions.assertThrows(InvalidRecordException.class, () -> Record.builder().type(Type.A).value(null).build());
    Assertions.assertThrows(InvalidRecordException.class, () -> new Record(Type.A, ""));
    Assertions.assertThrows(InvalidRecordException.class, () -> new Record(Type.A, "127.0.0.1", null));
    Assertions.assertThrows(InvalidRecordException.class, () -> new Record(Type.A, "127.0.0.1", -1L));
    Assertions.assertThrows(InvalidRecordException.class, () -> new Record(Type.A, "127.0.0.1.1", 10L));
    Assertions.assertThrows(InvalidRecordException.class, () -> new Record(Type.AAAA, "127.0.0.1.1", 10L));
    Assertions.assertThrows(InvalidDomainNameException.class, () -> new Record(Type.CNAME, "!@#!@#ASD", 10L));
  }
}
