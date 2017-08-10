package com.hll.push.core.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
public class Message {

  @ApiModelProperty(position = 1,value = "消息发送者",example = "001")
  private String from;

  @ApiModelProperty(position = 2,value = "消息接受者列表")
  private List<String> ids;

  @ApiModelProperty(position = 3,value = "消息内容",example = "这是一首简单的小情歌")
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
