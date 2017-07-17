package com.hll.push.queue;

import com.hll.push.core.model.Message;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 包装disruptor,提供一个实时消息的内存队列,并包含消息的处理器
 * Author: huangll
 * Written on 17/7/5.
 */
@Component
public class RealtimeMessageQueue implements ApplicationContextAware {

  private AtomicBoolean isStarted = new AtomicBoolean(false);

  @Autowired
  private PushMessageEventHandler pushMessageEventHandler;

  @Autowired
  private SaveMessageEventHandler saveMessageEventHandler;

  private Disruptor<Message> disruptor;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
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

    disruptor.handleEventsWith(saveMessageEventHandler).then(pushMessageEventHandler);

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
