package net.jfsanchez.dnsman.application.service;

import net.jfsanchez.dnsman.application.error.UnauthorizedException;
import net.jfsanchez.dnsman.application.port.output.AuthorizationPort;
import net.jfsanchez.dnsman.application.port.output.GetDomainPort;
import net.jfsanchez.dnsman.application.port.output.GetDomainsPort;
import net.jfsanchez.dnsman.application.port.output.PersistDomainPort;
import net.jfsanchez.dnsman.application.port.output.RemoveDomainPort;
import net.jfsanchez.dnsman.application.util.MockitoUtil;
import net.jfsanchez.dnsman.domain.entity.Domain;
import net.jfsanchez.dnsman.domain.error.DomainAlreadyExistsException;
import net.jfsanchez.dnsman.domain.valueobject.DomainName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class DomainServiceTest {
  private final GetDomainPort getDomainPort = Mockito.mock(GetDomainPort.class);
  private final GetDomainsPort getDomainsPort = Mockito.mock(GetDomainsPort.class);
  private final PersistDomainPort persistDomainPort = Mockito.mock(PersistDomainPort.class);
  private final RemoveDomainPort removeDomainPort = Mockito.mock(RemoveDomainPort.class);
  private final AuthorizationPort authorizationPort = Mockito.mock(AuthorizationPort.class);
  private final DomainService service = new DomainService(getDomainPort, getDomainsPort, persistDomainPort, removeDomainPort, authorizationPort);

  @BeforeEach
  void setup() {
    MockitoUtil.resetAll(this);
  }

  @Nested
  @DisplayName("RegisterDomainUseCase tests")
  class RegisterDomainUseCaseTest {
    @Test
    void it_fails_when_user_is_unauthorized() {
      final var domainName = DomainName.of("example.com");

      Mockito.when(authorizationPort.isAdminUser()).thenReturn(Mono.just(false));

      StepVerifier.create(service.registerDomain(domainName))
          .verifyError(UnauthorizedException.class);

      Mockito.verify(persistDomainPort, Mockito.never()).persistDomain(Mockito.any());
    }

    @Test
    void it_fails_when_domain_already_exists() {
      final var domainName = DomainName.of("example.com");

      Mockito.when(authorizationPort.isAdminUser()).thenReturn(Mono.just(true));
      Mockito.when(persistDomainPort.persistDomain(Mockito.any())).thenReturn(Mono.error(new DomainAlreadyExistsException(domainName)));

      StepVerifier.create(service.registerDomain(domainName))
          .verifyError(DomainAlreadyExistsException.class);

      Mockito.verify(persistDomainPort, Mockito.times(1)).persistDomain(Mockito.any());
    }

    @Test
    void it_registers_new_domain() {
      final var domainName = DomainName.of("example.com");

      Mockito.when(authorizationPort.isAdminUser()).thenReturn(Mono.just(true));
      Mockito.when(persistDomainPort.persistDomain(Mockito.any())).thenReturn(Mono.just(new Domain(domainName)));

      StepVerifier.create(service.registerDomain(domainName))
          .assertNext(domain -> Assertions.assertEquals(domainName, domain.domainName()))
          .verifyComplete();

      Mockito.verify(persistDomainPort, Mockito.times(1)).persistDomain(Mockito.any());
    }
  }
}
