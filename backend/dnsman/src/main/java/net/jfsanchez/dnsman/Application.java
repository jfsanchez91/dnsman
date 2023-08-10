package net.jfsanchez.dnsman;

import de.porsche.ho.chargepoint.infra.dns.DnsServer;
import io.micronaut.runtime.Micronaut;
import java.util.concurrent.Executors;

public class Application {
  public static void main(String[] args) {
    final var ctx = Micronaut.run(Application.class, args);
    final var dnsServer = ctx.getBean(DnsServer.class);
    Executors.newCachedThreadPool().submit(() -> dnsServer.start());
    Runtime.getRuntime().addShutdownHook(new Thread(dnsServer::stop));
  }
}
