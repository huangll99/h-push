package com.hll.push.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Entity
@Table(name = "push_message_receive")
public class MessageReceiveEntity {

  private String id;

  private String messageId;

  private String receiverId;

  private int status;

  private Date createTime;

  private Date updateTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
