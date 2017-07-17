package com.hll.push.rest.controller;

import com.hll.push.rest.PushResult;
import com.hll.push.core.model.Message;
import com.hll.push.queue.RealtimeMessageQueue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@RestController
@RequestMapping("/api")
@Api(value = "push", produces = MediaType.APPLICATION_JSON_VALUE)

public class RealtimeMessageController {

  @Autowired
  RealtimeMessageQueue realtimeMessageQueue;


  @ApiOperation(value = "推送实时消息接口")
  @RequestMapping(value = "/message", method = RequestMethod.POST)
  public PushResult pushRealtimeMessage(@RequestBody Message message) {
    realtimeMessageQueue.put(message);

    return PushResult.builder().success(true).msg("ok").build();
  }

}
