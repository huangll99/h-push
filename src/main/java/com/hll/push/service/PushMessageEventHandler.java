package com.hll.push.service;

import com.hll.push.core.model.Message;
import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Component("pushMessageEventHandler")
public class PushMessageEventHandler implements EventHandler<Message> {

  @Override
  public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {
    //推送消息


  }

}
