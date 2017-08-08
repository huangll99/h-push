package com.hll.push.websocket.handler;

import com.hll.push.OfflineMessagePushTask;
import com.hll.push.websocket.ClientIdToChannelMap;
import com.hll.push.websocket.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ExecutorService;

/**
 * 处理client的连接与断开,绑定clientId和channel
 * Author: huangll
 * Written on 17/6/29.
 */
@Component
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

  private static final Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

  @Autowired
  ClientIdToChannelMap clientIdToChannelMap;

  @Autowired
  ExecutorService offlineMessagePushExecutor;

  private AttributeKey<String> CLIENT_ID = AttributeKey.newInstance("clientId");

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    String json = msg.text();
    Packet packet = Packet.from(json);

    switch (packet.getType()) {
      case Packet.Type.CONNECT:
        handleConnect(packet, ctx.channel());
        break;
    }

  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    String clientId = ctx.channel().attr(CLIENT_ID).get();
    if (!StringUtils.isEmpty(clientId)) {
      clientIdToChannelMap.removeChannel(clientId);
      logger.info("client " + clientId + " disconnected.");
    }
  }

  private void handleConnect(Packet packet, Channel channel) {
    String clientId = (String) packet.getMsg();
    if (!StringUtils.isEmpty(clientId)) {
      clientIdToChannelMap.addChannel(clientId, channel);
      channel.attr(CLIENT_ID).set(clientId);
      logger.info("client " + clientId + " connected !!!");

      //response
      Packet response = Packet.newInstance();
      response.setType(Packet.Type.CONNECTED);
      response.setMsg("connected !!!");
      TextWebSocketFrame frame = new TextWebSocketFrame(response.toJson());
      channel.writeAndFlush(frame);

      //异步处理用户连接事件
      offlineMessagePushExecutor.submit(new OfflineMessagePushTask(clientId));
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
   /* System.out.println("xxxxxxxxxxxxx");
    Packet response = Packet.newInstance();
    response.setType(Packet.Type.CONNECTED);
    response.setMsg("connected !!!");
    TextWebSocketFrame frame = new TextWebSocketFrame(response.toJson());
    ctx.channel().writeAndFlush(frame);*/
  }
}

