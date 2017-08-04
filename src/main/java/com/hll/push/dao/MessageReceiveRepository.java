package com.hll.push.dao;

import com.hll.push.entities.MessageReceiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
public interface MessageReceiveRepository extends JpaRepository<MessageReceiveEntity, String> {

  @Transactional
  @Modifying
  @Query(value = "update MessageReceiveEntity mr set mr.status=:status where mr.messageId=:messageId and mr.receiverId=:receiverId")
  void updateStatus(@Param("messageId") String messageId, @Param("receiverId") String receiverId, @Param("status") int status);

}
