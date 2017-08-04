package com.hll.push.queue;

import com.google.common.collect.Maps;
import com.hll.push.core.model.Message;
import com.hll.push.enums.MessageStatus;
import com.hll.push.service.MessageService;
import com.hll.push.utils.JsonUtil;
import com.hll.push.websocket.ClientIdToChannelMap;
import com.hll.push.websocket.Packet;
import com.lmax.disruptor.EventHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 消息的事件处理器
 * Author: huangll
 * Written on 17/8/4.
 */
@Component
public class MessageEventHandler implements EventHandler<Message> {

  private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);

  @Autowired
  private MessageService messageService;

  @Autowired
  ClientIdToChannelMap clientIdToChannelMap;

  @Override
  public void onEvent(Message message, long sequence, boolean endOfBatch) throws Exception {

    //将消息保存到数据库
    String messageId = messageService.save(message);

    //推送消息
    message.getIds().stream().filter(receiveId -> clientIdToChannelMap.contain(receiveId)).forEach(receiveId -> {
      HashMap<String, Object> msg = Maps.newHashMap();
      msg.put("from", message.getFrom());
      msg.put("to", receiveId);
      msg.put("content", message.getContent());


      Packet response = Packet.newInstance();
      response.setType(Packet.Type.PUSH);
      response.setMsg(msg);
      TextWebSocketFrame frame = new TextWebSocketFrame(response.toJson());

      clientIdToChannelMap.getChannel(receiveId).writeAndFlush(frame);

      logger.info("推送消息: {}", JsonUtil.toJson(msg));

      //更新消息的数据库状态
      messageService.updateStatus(messageId, receiveId, MessageStatus.Pushed.value());
    });
  }
}
