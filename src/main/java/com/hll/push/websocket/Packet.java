package com.hll.push.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Author: huangll
 * Written on 17/7/1.
 */
public class Packet {

  private final static ObjectMapper mapper = new ObjectMapper();

  private int type;
  private String msg;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public static Packet from(String json) {
    try {
      return mapper.readValue(json, Packet.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String toJson() {
    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static Packet newInstance() {
    return new Packet();
  }

  public static class Type {
    public static final int CONNECT = 0;
    public static final int CONNECTED = 1;
    public static final int PUSH = 2;
  }
}
