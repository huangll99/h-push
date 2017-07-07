package com.hll.push.rest;

/**
 * Author: huangll
 * Written on 17/7/5.
 */
public class PushResult {
  // 调用是否成功
  private boolean success;
  // 接口提示消息
  private String msg;

  public PushResult(boolean success, String msg) {
    this.success = success;
    this.msg = msg;
  }

  public PushResult() {
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public static class Builder {
    private boolean success;
    private String msg;

    public Builder success(boolean isSuccess) {
      this.success = isSuccess;
      return this;
    }

    public Builder msg(String msg) {
      this.msg = msg;
      return this;
    }

    public PushResult build() {
      return new PushResult(this.success, this.msg);
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}