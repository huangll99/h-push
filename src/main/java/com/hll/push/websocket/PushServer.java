package com.hll.push.websocket;

import com.hll.push.websocket.handler.TextWebSocketFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 推送服务,维护websocket长连接,推送消息
 * Author: huangll
 * Written on 17/6/29.
 */
@Component
public class PushServer implements ApplicationListener<ApplicationEvent> {

  private static Logger logger = LoggerFactory.getLogger(PushServer.class);

  @Autowired
  private TextWebSocketFrameHandler textWebSocketFrameHandler;

  private AtomicBoolean started = new AtomicBoolean(false);

  @Value("${push.port}")
  private int port;

  private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
  private EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

  @Override
  public void onApplicationEvent(ApplicationEvent applicationEvent) {
    //spring容器启动之后,启动推送服务
    start();
  }

  private void start() {

    //保证只能启动一次
    if (started.compareAndSet(false, true)) {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast(new HttpServerCodec());
              pipeline.addLast(new HttpObjectAggregator(64 * 1024));
              pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
              pipeline.addLast(textWebSocketFrameHandler);
            }
          });

      ChannelFuture f = bootstrap.bind(new InetSocketAddress(port));
      f.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          if (future.isSuccess()) {
            logger.info("push server bind on port: " + port);
          } else {
            logger.error("push server bind error: ", future.cause());
          }
        }
      });
    }
  }
}
