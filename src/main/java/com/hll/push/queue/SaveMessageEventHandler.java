package com.hll.push.queue;

import com.hll.push.core.model.Message;
import com.hll.push.service.MessageService;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Component("saveMessageEventHandler")
public class SaveMessageEventHandler implements EventHandler<Message> {

  @Autowired
  private MessageService messageService;

  @Override
  public void onEvent(Message message, long sequence, boolean endOfBatch) throws Exception {
    //将消息保存到数据库
    messageService.save(message);
  }
}
