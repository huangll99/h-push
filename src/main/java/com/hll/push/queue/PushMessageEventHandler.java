package com.hll.push.queue;

import com.google.common.collect.Maps;
import com.hll.push.core.model.Message;
import com.hll.push.util.JsonUtil;
import com.hll.push.websocket.ClientIdToChannelMap;
import com.lmax.disruptor.EventHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Component("pushMessageEventHandler")
public class PushMessageEventHandler implements EventHandler<Message> {

  @Autowired
  ClientIdToChannelMap clientIdToChannelMap;

  @Override
  public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {

    //推送消息
    event.getIds().stream().filter(id -> clientIdToChannelMap.contain(id)).forEach(id -> {
      HashMap<String, Object> msg = Maps.newHashMap();
      msg.put("from", event.getFrom());
      msg.put("to", id);
      msg.put("content", event.getContent());

      TextWebSocketFrame frame = new TextWebSocketFrame(JsonUtil.toJson(msg));
      clientIdToChannelMap.getChannel(id).writeAndFlush(frame);
    });

  }

}
