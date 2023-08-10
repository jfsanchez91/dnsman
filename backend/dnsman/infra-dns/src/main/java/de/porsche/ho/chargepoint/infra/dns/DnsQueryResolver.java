package de.porsche.ho.chargepoint.infra.dns;

import de.porsche.ho.chargepoint.infra.dns.service.DnsApiService;
import de.porsche.ho.chargepoint.infra.dns.service.dto.DnsRecordResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.dns.DatagramDnsQuery;
import io.netty.handler.codec.dns.DatagramDnsResponse;
import io.netty.handler.codec.dns.DefaultDnsRawRecord;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsSection;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
class DnsQueryResolver extends SimpleChannelInboundHandler<DatagramDnsQuery> {
  private final DnsApiService service;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, DatagramDnsQuery query) {
    log.debug("New DNS Query received (query={}).", query);
    final var question = query.recordAt(DnsSection.QUESTION);
    final var domainName = question.name();
    final var recordType = question.type();
    final var recordClass = question.dnsClass();

    final var response = new DatagramDnsResponse(query.recipient(), query.sender(), query.id(), DnsOpCode.QUERY);
    response.setRecursionAvailable(false);

    service.resolve(recordType, domainName)
        .filter(it -> it.data() != null)
        .map(DnsRecordResponse::data)
        .map(data -> {
          final var buffer = ctx.alloc().buffer();
          buffer.writeBytes(data);
          return new DefaultDnsRawRecord(domainName, recordType, recordClass, buffer);
        })
        .doOnNext(record -> response.addRecord(DnsSection.ANSWER, record))
        .doFinally(ignored -> ctx.writeAndFlush(response))
        .subscribe();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable error) {
    log.error("An error occurred while processing the DNS Query.", error);
  }
}
