package com.hll.push.service;

import com.hll.push.core.model.Message;
import com.hll.push.core.model.MessageReadMark;
import com.hll.push.core.model.MessageReceive;
import com.hll.push.core.model.MessageSearch;
import com.hll.push.dao.MessageReceiveRepository;
import com.hll.push.dao.MessageRepository;
import com.hll.push.entities.MessageEntity;
import com.hll.push.entities.MessageReceiveEntity;
import com.hll.push.enums.MessageStatus;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.*;
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

  @Transactional
  public void updateStatus(String messageId, String receiveId, int status) {
    messageReceiveRepository.updateStatus(messageId, receiveId, status);
  }

  public List<Map<String, Object>> getUnpushedMessages(String clientId) {
    //查询未推送消息
    return messageRepository.getUnpushedMessages(clientId);
  }

  static FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

  public Page<MessageEntity> messageList(MessageSearch messageSearch) {
    MessageEntity messageEntity = new MessageEntity();

    Page<MessageEntity> page = messageRepository.findAll(new Specification<MessageEntity>() {
      @Override
      public Predicate toPredicate(Root<MessageEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path<String> sendId = root.get("sendId");
        Path<String> key = root.get("content");
        Path<Date> createTime = root.get("createTime");

        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(messageSearch.getKey())) {
          predicates.add(criteriaBuilder.like(key, "%" + messageSearch.getKey() + "%"));
        }
        if (!StringUtils.isEmpty(messageSearch.getSendId())) {
          predicates.add(criteriaBuilder.equal(sendId, messageSearch.getSendId()));
        }

        try {
          if (!StringUtils.isEmpty(messageSearch.getStartTime())) {
            predicates.add(criteriaBuilder.greaterThan(createTime, fastDateFormat.parse(messageSearch.getStartTime())));
          }
          if (!StringUtils.isEmpty(messageSearch.getEndTime())) {
            predicates.add(criteriaBuilder.lessThan(createTime, fastDateFormat.parse(messageSearch.getEndTime())));
          }
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0])); //这里可以设置任意条查询条件

        return criteriaQuery.getRestriction();
      }
    }, new PageRequest(messageSearch.getPage(), messageSearch.getPageSize(), new Sort(Sort.Direction.DESC, "createTime")));
    return page;
  }

  public Optional<Integer> getUnreadMsgCount(String userId) {
    Integer unreadMsgCount = messageReceiveRepository.getUnreadMsgCount(userId);
    return Optional.of(unreadMsgCount);
  }

  public List<MessageEntity> getUnreadMsgList(String userId) {
    return messageRepository.getUnreadMsgList(userId);
  }

  @Transactional
  public void markMsgRead(MessageReadMark messageReadMark) {
    messageReadMark.getMsgIds().stream().forEach(
        msgId -> messageReceiveRepository.updateStatus(msgId, messageReadMark.getUserId(), MessageStatus.Read.value())
    );
  }

  public List<MessageReceiveEntity> getMessageReceives(String messageId) {
    List<MessageReceiveEntity> list = messageReceiveRepository.findByMessageId(messageId);
    return list;
  }
}
