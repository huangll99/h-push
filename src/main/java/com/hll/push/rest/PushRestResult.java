package com.hll.push.rest;

/**
 * Author: huangll
 * Written on 17/8/10.
 */
public class PushRestResult<T> {
  // 调用是否成功
  private boolean success;
  // 接口提示消息
  private String msg;
  // 数据
  private T data;

  public PushRestResult() {
  }

  public PushRestResult(boolean success, String msg, T data) {
    this.success = success;
    this.msg = msg;
    this.data = data;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
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

  public static class Builder<T> {
    private boolean success;
    private String msg;
    private T data;

    public PushRestResult.Builder success(boolean isSuccess) {
      this.success = isSuccess;
      return this;
    }

    public PushRestResult.Builder msg(String msg) {
      this.msg = msg;
      return this;
    }

    public PushRestResult.Builder data(T data) {
      this.data = data;
      return this;
    }

    public PushRestResult build() {
      return new PushRestResult<T>(this.success, this.msg, this.data);
    }
  }

  public static PushRestResult.Builder builder() {
    return new PushRestResult.Builder();
  }

}
