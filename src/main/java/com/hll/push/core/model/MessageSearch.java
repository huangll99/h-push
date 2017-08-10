package com.hll.push.core.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: huangll
 * Written on 17/8/10.
 */
public class MessageSearch extends PageRequest{

  @ApiModelProperty(position = 3, value = "搜索关键字", example = "火星")
  private String key;

  @ApiModelProperty(position = 4, value = "消息推送起始时间", example = "2017-08-08 10:00:00")
  private String startTime;

  @ApiModelProperty(position = 5, value = "消息推送结束时间", example = "2018-08-08 10:00:00")
  private String endTime;

  @ApiModelProperty(position = 6, value = "发送人id", example = "002")
  private String sendId;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getSendId() {
    return sendId;
  }

  public void setSendId(String sendId) {
    this.sendId = sendId;
  }
}
