package com.hll.push.core.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: huangll
 * Written on 17/8/10.
 */
public class PageRequest {

  @ApiModelProperty(position = 1, value = "第几页", example = "1")
  private int page;

  @ApiModelProperty(position = 2, value = "每页多少条", example = "20")
  private int pageSize;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
