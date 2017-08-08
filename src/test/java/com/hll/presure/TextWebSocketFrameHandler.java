package com.hll.presure;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 处理client的连接与断开,绑定clientId和channel
 * Author: huangll
 * Written on 17/6/29.
 */
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

  AtomicInteger i = new AtomicInteger(0);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    System.out.println(msg.text());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {

  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    /*System.out.println("connected...");
    Packet response = Packet.newInstance();
    response.setType(Packet.Type.CONNECT);
    response.setMsg("00" + i.get());
    TextWebSocketFrame frame = new TextWebSocketFrame(response.toJson());
    ctx.channel().writeAndFlush(frame);*/
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
  }
}
