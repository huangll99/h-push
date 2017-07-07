package com.hll.push.dao;

import com.hll.push.entities.MessageEntity;
import com.hll.push.entities.MessageReceiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
public interface MessageReceiveRepository extends JpaRepository<MessageReceiveEntity, String> {

}
