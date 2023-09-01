package net.jfsanchez.dnsman.infra.web.config;

import io.micronaut.serde.annotation.SerdeImport;
import net.jfsanchez.dnsman.domain.valueobject.Record;

@SerdeImport(Record.class)
class SerializationConfig {

}
