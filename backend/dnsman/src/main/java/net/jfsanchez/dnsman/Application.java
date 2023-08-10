package net.jfsanchez.dnsman;

import de.porsche.ho.chargepoint.infra.dns.DnsServer;
import io.micronaut.runtime.Micronaut;
import java.util.concurrent.Executors;
import net.jfsanchez.dnsman.config.DnsServerConfig;

public class Application {
  public static void main(String[] args) {
    final var ctx = Micronaut.run(Application.class, args);
    final var dnsServer = ctx.getBean(DnsServer.class);
    final var dnsServerConfig = ctx.getBean(DnsServerConfig.class);

    Executors.newCachedThreadPool().submit(() -> dnsServer.start(dnsServerConfig.port()));
    Runtime.getRuntime().addShutdownHook(new Thread(dnsServer::stop));
  }
}
