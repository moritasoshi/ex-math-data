package com.example.domain;

import java.util.List;

public class ItemPage extends Item {
  private List<Item> itemList;
  // ページング関連
  private Integer size;
  private Integer page;
  private Integer pageSize;
  private Integer totalPage;

  public ItemPage(String name, String brand, String parent, String child, String grandChild, Integer page) {
    super(name, brand, parent, child, grandChild);
    this.page = page;
  }

  public ItemPage() {
  }

  public List<Item> getItemList() {
    return this.itemList;
  }

  public void setItemList(List<Item> itemList) {
    this.itemList = itemList;
  }

  public Integer getSize() {
    return this.size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getPage() {
    return this.page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getTotalPage() {
    return this.totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

}