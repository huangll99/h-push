package com.hll.push.service;

import com.hll.push.core.model.Message;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Component
public class RealtimeMessageQueue implements ApplicationListener<ApplicationEvent> {

  private AtomicBoolean isStarted = new AtomicBoolean(false);

  @Autowired
  @Qualifier("saveMessageEventHandler")
  private EventHandler saveDBEventHandler;

  @Autowired
  @Qualifier("pushMessageEventHandler")
  private EventHandler pushMessageEventHandler;

  private Disruptor<Message> disruptor;

  @Override
  public void onApplicationEvent(ApplicationEvent applicationEvent) {
    if (isStarted.compareAndSet(false, true)) {
      startQueue();
    }
  }

  private void startQueue() {
    EventFactory<Message> eventFactory = Message::new;
    ThreadFactory threadFactory = r -> {
      Thread thread = new Thread(r);
      thread.setName("disruptor-");
      thread.setDaemon(true);
      return thread;
    };

    disruptor = new Disruptor<>(eventFactory, 1024 * 1024, threadFactory, ProducerType.MULTI, new BlockingWaitStrategy());

    disruptor.handleEventsWith(saveDBEventHandler).then(pushMessageEventHandler);

    disruptor.start();
  }


  /**
   * 将消息放入队列
   *
   * @param message 消息
   */
  public void put(Message message) {
    disruptor.getRingBuffer().publishEvent(((event, sequence) -> {
      BeanUtils.copyProperties(message, event);
    }));
  }
}
