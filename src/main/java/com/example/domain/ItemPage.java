package com.example.domain;

import java.util.List;

import lombok.Data;

@Data
public class ItemPage {
  private List<Item> itemList;
  // ページング関連
  private Integer size;

  public ItemPage(List<Item> itemList, Integer size) {
    this.itemList = itemList;
    this.size = size;
  }

  public ItemPage() {
  }
}