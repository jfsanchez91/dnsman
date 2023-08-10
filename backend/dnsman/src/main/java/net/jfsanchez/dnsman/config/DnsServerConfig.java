package net.jfsanchez.dnsman.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.AccessorsStyle;

@ConfigurationProperties("micronaut.server.dns")
@AccessorsStyle(readPrefixes = {""})
public interface DnsServerConfig {
  int port();
}
