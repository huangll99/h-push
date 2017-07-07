package com.hll.push.core.model;

import java.util.List;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
public class Message {

  private String from;

  private List<String> ids;

  private String content;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public List<String> getIds() {
    return ids;
  }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
