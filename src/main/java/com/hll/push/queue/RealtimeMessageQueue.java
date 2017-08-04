package com.hll.push.queue;

import com.hll.push.core.model.Message;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

/**
 * 包装disruptor,提供一个实时消息的内存队列,并包含消息的处理器
 * Author: huangll
 * Written on 17/7/5.
 */
@Component("realtimeMessageQueue")
public class RealtimeMessageQueue {

  @Autowired
  private MessageEventHandler messageEventHandler;


  private Disruptor<Message> disruptor;


  public void start() {
    EventFactory<Message> eventFactory = Message::new;
    ThreadFactory threadFactory = r -> {
      Thread thread = new Thread(r);
      thread.setName("disruptor-");
      thread.setDaemon(true);
      return thread;
    };

    disruptor = new Disruptor<>(eventFactory, 1024 * 1024, threadFactory, ProducerType.MULTI, new BlockingWaitStrategy());

    disruptor.handleEventsWith(messageEventHandler);

    disruptor.start();
  }


  /**
   * 将消息放入队列
   *
   * @param message 消息
   */
  public void put(Message message) {
    disruptor.getRingBuffer().publishEvent((event, sequence) -> BeanUtils.copyProperties(message, event));
  }

}
