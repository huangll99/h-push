package com.hll.push.websocket;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Author: huangll
 * Written on 17/6/29.
 */
@Component("clientIdToChannelMap")
public class ClientIdToChannelMap {

  /**
   * 维护client id和连接的关系,用于根据id查找channel
   */
  private final Map<String, Channel> clientIdToChannelMap = Maps.newConcurrentMap();

  public void addChannel(String clientId, Channel channel) {
    clientIdToChannelMap.put(clientId, channel);
  }

  public Channel getChannel(String clientId) {
    return clientIdToChannelMap.get(clientId);
  }

  public void removeChannel(String clientId) {
    clientIdToChannelMap.remove(clientId);
  }

  public boolean contain(String id) {
    return clientIdToChannelMap.containsKey(id);
  }

  public Set<String> onlineClients() {
    return clientIdToChannelMap.keySet();
  }
}
