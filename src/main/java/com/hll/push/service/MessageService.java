package com.hll.push.service;

import com.hll.push.core.model.Message;
import com.hll.push.dao.MessageReceiveRepository;
import com.hll.push.dao.MessageRepository;
import com.hll.push.entities.MessageEntity;
import com.hll.push.entities.MessageReceiveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MessageReceiveRepository messageReceiveRepository;

  @Transactional
  public String save(Message message) {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.setSendId(message.getFrom());
    messageEntity.setContent(message.getContent());
    messageEntity.setCreateTime(new Date());

    messageRepository.save(messageEntity);

    List<MessageReceiveEntity> receivers = message.getIds().stream().map(receiverId -> {
      MessageReceiveEntity messageReceiveEntity = new MessageReceiveEntity();
      messageReceiveEntity.setMessageId(messageEntity.getId());
      messageReceiveEntity.setReceiverId(receiverId);
      messageReceiveEntity.setStatus(0);
      messageReceiveEntity.setCreateTime(new Date());
      messageReceiveEntity.setUpdateTime(new Date());
      return messageReceiveEntity;
    }).collect(Collectors.toList());

    messageReceiveRepository.save(receivers);

    return messageEntity.getId();
  }

  public void updateStatus(String messageId, String receiveId, int status) {
    messageReceiveRepository.updateStatus(messageId, receiveId, status);
  }
}
