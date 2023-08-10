package de.porsche.ho.chargepoint.infra.dns;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.dns.DatagramDnsQueryDecoder;
import io.netty.handler.codec.dns.DatagramDnsResponseEncoder;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class DnsServer {
  public static final int DEFAULT_PORT = 1053;

  private final DnsQueryResolver dnsQueryResolver;
  private Channel serverChannel;

  public void start() {
    start(DEFAULT_PORT);
  }

  public void start(int port) {
    final var group = new NioEventLoopGroup();
    try {
      final var bootstrap = new Bootstrap();
      bootstrap.group(group)
          .channel(NioDatagramChannel.class)
          .handler(new ChannelInitializer<DatagramChannel>() {
            @Override
            protected void initChannel(DatagramChannel ch) throws Exception {
              log.debug("Initializing DNS Server channel.");
              ch.pipeline()
                  .addLast(new DatagramDnsQueryDecoder())
                  .addLast(new DatagramDnsResponseEncoder())
                  .addLast(dnsQueryResolver);
              log.info("DNS Server Running (port={})", port);
            }
          });
      final var future = bootstrap.bind(port).sync();
      serverChannel = future.channel();
      serverChannel.closeFuture().sync();
    } catch (InterruptedException e) {
      log.error("DNS Server error", e);
    } finally {
      group.shutdownGracefully();
    }
  }

  public void stop() {
    if (serverChannel != null) {
      log.info("Stopping DNS server gracefully");
      try {
        serverChannel.closeFuture().sync();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
