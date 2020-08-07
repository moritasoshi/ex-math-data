package com.example.domain;

import java.util.List;

import lombok.Data;

@Data
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
}