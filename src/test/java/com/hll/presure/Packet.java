package com.hll.presure;

import com.alibaba.fastjson.JSON;

/**
 * Author: huangll
 * Written on 17/7/1.
 */
public class Packet {


  private int type;
  private Object msg;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Object getMsg() {
    return msg;
  }

  public void setMsg(Object msg) {
    this.msg = msg;
  }


  public String toJson() {
    return JSON.toJSONString(this);
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
