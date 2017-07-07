package com.hll.push.rest.controller;

import com.hll.push.rest.PushResult;
import com.hll.push.core.model.Message;
import com.hll.push.service.RealtimeMessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@RestController
public class RealtimeMessageController {

  @Autowired
  RealtimeMessageQueue realtimeMessageQueue;

  @RequestMapping("/message")
  public PushResult pushRealtimeMessage(@RequestBody Message message) {
    realtimeMessageQueue.put(message);

    return PushResult.builder().success(true).msg("ok").build();
  }

}
