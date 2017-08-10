package com.hll.push.core.model;

import java.util.List;

/**
 * Author: huangll
 * Written on 17/8/10.
 */
public class MessageReadMark {
  private String userId;

  private List<String> msgIds;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<String> getMsgIds() {
    return msgIds;
  }

  public void setMsgIds(List<String> msgIds) {
    this.msgIds = msgIds;
  }
}
