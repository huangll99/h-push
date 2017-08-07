package com.hll.push.dao;

import com.hll.push.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
public interface MessageRepository extends JpaRepository<MessageEntity, String> {

  @Query("select new Map( me.id as id,me.content as content,me.sendId as sendId ,mre.receiverId as receiverId) from MessageEntity me,MessageReceiveEntity mre where me.id=mre.messageId and mre.receiverId=:clientId and mre.status=0")
  List<Map<String,Object>> getUnpushedMessages(@Param("clientId") String clientId);
}
