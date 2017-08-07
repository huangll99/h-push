package com.hll.push;

import com.google.common.collect.Maps;
import com.hll.push.enums.MessageStatus;
import com.hll.push.service.MessageService;
import com.hll.push.utils.JsonUtil;
import com.hll.push.utils.SpringHelper;
import com.hll.push.websocket.ClientIdToChannelMap;
import com.hll.push.websocket.Packet;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: huangll
 * Written on 17/8/4.
 */
public class OfflineMessagePushTask implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(OfflineMessagePushTask.class);

  private String clientId;


  public OfflineMessagePushTask(String clientId) {
    this.clientId = clientId;
  }

  @Override
  public void run() {
    //查询该clientId的未推送消息
    MessageService messageService = SpringHelper.getBean("messageService");
    List<Map<String, Object>> messages = messageService.getUnpushedMessages(clientId);

    //推送消息
    ClientIdToChannelMap clientIdToChannelMap = SpringHelper.getBean("clientIdToChannelMap");
    Channel channel = clientIdToChannelMap.getChannel(clientId);

    messages.stream().forEach(map -> {

      HashMap<String, Object> msg = Maps.newHashMap();
      msg.put("from", map.get("sendId"));
      msg.put("to", clientId);
      msg.put("content", map.get("content"));

      Packet response = Packet.newInstance();
      response.setType(Packet.Type.PUSH);
      response.setMsg(msg);
      TextWebSocketFrame frame = new TextWebSocketFrame(response.toJson());

      channel.write(frame);

      logger.info("推送消息: {}", JsonUtil.toJson(map));

      messageService.updateStatus((String) map.get("id"), clientId, MessageStatus.Pushed.value());
    });

    channel.flush();

  }
}
