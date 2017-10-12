package com.hll.push.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Entity
@Table(name = "push_message_receive")
public class MessageReceiveEntity {

  @Id
  @Column(name = "id", length = 32)
  @NotEmpty
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @GeneratedValue(generator = "system-uuid")
  private String id;

  @Column(name = "message_id", length = 32)
  @NotEmpty
  private String messageId;

  @Column(name = "receiver_id", length = 32)
  @NotEmpty
  private String receiverId;

  @Column(name = "status")
  private int status;

  @Column(name = "create_time", columnDefinition = "timestamp")
  private Date createTime;

  @Column(name = "update_time", columnDefinition = "timestamp")
  private Date updateTime;

  @Column(name = "retry_times")
  private int retryTimes;

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

  public int getRetryTimes() {
    return retryTimes;
  }

  public void setRetryTimes(int retryTimes) {
    this.retryTimes = retryTimes;
  }
}
