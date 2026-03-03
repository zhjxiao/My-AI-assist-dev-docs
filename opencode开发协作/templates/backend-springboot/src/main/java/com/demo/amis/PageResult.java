package com.demo.amis;

import java.util.List;

public class PageResult<T> {
  public List<T> items;
  public long total;
  public PageResult(List<T> items, long total) {
    this.items = items;
    this.total = total;
  }
}
