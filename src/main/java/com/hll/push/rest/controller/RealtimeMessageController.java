package com.hll.push.rest.controller;

import com.hll.push.core.model.MessageReadMark;
import com.hll.push.core.model.MessageSearch;
import com.hll.push.entities.MessageEntity;
import com.hll.push.rest.PushRestResult;
import com.hll.push.rest.PushResult;
import com.hll.push.core.model.Message;
import com.hll.push.queue.RealtimeMessageQueue;
import com.hll.push.service.MessageService;
import com.hll.push.websocket.ClientIdToChannelMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api")
@Api(value = "push", produces = MediaType.APPLICATION_JSON_VALUE)
public class RealtimeMessageController {

  @Autowired
  RealtimeMessageQueue realtimeMessageQueue;

  @Autowired
  MessageService messageService;

  @Autowired
  ClientIdToChannelMap clientIdToChannelMap;

  @ApiOperation(value = "推送实时消息接口")
  @RequestMapping(value = "/message", method = RequestMethod.POST)
  public PushResult pushRealtimeMessage(@RequestBody Message message) {
    realtimeMessageQueue.put(message);

    return PushResult.builder().success(true).msg("ok").build();
  }

  @ApiOperation(value = "消息查询接口")
  @RequestMapping(value = "/messageList", method = RequestMethod.POST)
  public PushRestResult<Page<MessageEntity>> messageList(@RequestBody MessageSearch messageSearch) {

    Page<MessageEntity> page = messageService.messageList(messageSearch);

    return PushRestResult.builder().success(true).msg("ok").data(page).build();
  }

  @ApiOperation(value = "查询用户未读消息数量")
  @RequestMapping(value = "/unreadMsgCount/{userId}", method = RequestMethod.GET)
  public PushRestResult<Integer> unreadMsgCount(@PathVariable("userId") String userId) {
    Optional<Integer> unreadMsgCount = messageService.getUnreadMsgCount(userId);
    if (unreadMsgCount.isPresent()) {
      return PushRestResult.builder().success(true).msg("ok").data(unreadMsgCount.get()).build();
    } else {
      return PushRestResult.builder().success(false).msg("用户不存在,userId:" + userId).build();
    }
  }

  @ApiOperation(value = "查询用户未读消息列表")
  @RequestMapping(value = "/unreadMsgList/{userId}", method = RequestMethod.GET)
  public PushRestResult<List<MessageEntity>> unreadMsgList(@PathVariable("userId") String userId) {
    List<MessageEntity> msgList = messageService.getUnreadMsgList(userId);
    return PushRestResult.builder().success(true).msg("ok").data(msgList).build();
  }

  @ApiOperation(value = "标记消息为已读")
  @RequestMapping(value = "/markMsgRead", method = RequestMethod.POST)
  public PushResult markMsgRead(@RequestBody MessageReadMark messageReadMark) {
    messageService.markMsgRead(messageReadMark);
    return PushResult.builder().success(true).msg("ok").build();
  }

  @ApiOperation(value = "在线用户列表")
  @RequestMapping(value = "/onlineUsers", method = RequestMethod.GET)
  public PushRestResult<Set<String>> onlineUsers() {

    return PushRestResult.builder().success(true).msg("ok").data(clientIdToChannelMap.onlineClients()).build();
  }
}
