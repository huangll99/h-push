package com.hll.presure;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 压测工具
 * Author: huangll
 * Written on 17/8/7.
 */
public class PresureTool {

  public static void main(String[] args) throws URISyntaxException, InterruptedException {
    NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(10);

    final URI uri = new URI("ws://localhost:9999/ws");

    for (int i = 0; i < 4000; i++) {
      final WebSocketClientHandler handler =
          new WebSocketClientHandler(
              WebSocketClientHandshakerFactory.newHandshaker(
                  uri, WebSocketVersion.V13, null, false, HttpHeaders.EMPTY_HEADERS, 1280000));

      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(eventLoopGroup)
          .channel(NioSocketChannel.class)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast("http-codec", new HttpClientCodec());
              pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
              pipeline.addLast("ws-handler", handler);
              //socketChannel.pipeline().addLast(new TextWebSocketFrameHandler());
            }
          });
      Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
      handler.handshakeFuture().sync();

      Packet response = Packet.newInstance();
      response.setType(Packet.Type.CONNECT);
      response.setMsg("00" + i);
      TextWebSocketFrame frame = new TextWebSocketFrame(response.toJson());
      channel.writeAndFlush(frame).sync();
    }

  }
  //}
}
