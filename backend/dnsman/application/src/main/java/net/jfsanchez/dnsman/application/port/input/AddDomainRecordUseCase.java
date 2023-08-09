package net.jfsanchez.dnsman.application.port.input;

import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.RecordAlreadyExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import net.jfsanchez.dnsman.domain.valueobject.Type;
import reactor.core.publisher.Mono;

public interface AddDomainRecordUseCase {
  Mono<Domain> addDomainRecord(DomainName domainName, Type recordType, String recordValue)
      throws RecordAlreadyExistsException, UnauthorizedException;
}
