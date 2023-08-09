package net.jfsanchez.dnsman.application.port.input;

import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainDoesNotExistsException;
import net.jfsanchez.dnsman.domain.error.RecordDoesNotExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import reactor.core.publisher.Mono;

public interface RemoveDomainRecordUseCase {
  Mono<Domain> removeDnsRecord(DomainName domainName, Type type, String recordValue) throws DomainDoesNotExistsException, RecordDoesNotExistsException,
      UnauthorizedException;
}
