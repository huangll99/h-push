package com.hll.push.websocket;

import com.hll.push.websocket.server.PushServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Author: huangll
 * Written on 17/6/29.
 */
@Component
public class PushServerStartListener implements ApplicationListener<ApplicationEvent> {

  @Autowired
  PushServer pushServer;

  @Override
  public void onApplicationEvent(ApplicationEvent applicationEvent) {
    //spring容器启动之后,启动推送服务
    pushServer.start();
  }
}
