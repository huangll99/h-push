package com.hll.push.enums;

/**
 * Author: huangll
 * Written on 17/8/4.
 */
public enum MessageStatus {

  Created(0), Pushed(1), Read(1);

  private int status;

  MessageStatus(int status) {
    this.status = status;
  }

  public int value() {
    return this.status;
  }
}
