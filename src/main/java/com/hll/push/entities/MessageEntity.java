package com.hll.push.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
@Entity
@Table(name = "push_message")
public class MessageEntity {

  @Id
  @Column(name = "id", length = 32)
  @NotEmpty
  @GeneratedValue
  private String id;

  @Column(name = "send_id", length = 32)
  @NotEmpty
  private String sendId;

  @Column(name = "content", length = 256)
  @NotEmpty
  private String content;

  @Column(name = "create_time")
  @NotEmpty
  private Date createTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSendId() {
    return sendId;
  }

  public void setSendId(String sendId) {
    this.sendId = sendId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
