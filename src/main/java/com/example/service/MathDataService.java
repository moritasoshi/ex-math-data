package com.example.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domain.*;
import com.example.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MathDataService {
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private ItemRepository itemRepository;

  private static final int PAGE_SIZE = 30;
  
  public List<Item> showAllItems() {
    List<Item> itemList = itemRepository.findByPage();
    return itemList;
  }
  public List<Item> showAllItems(Item item) {
    // 全て記述されている場合
    List<Item> itemList = itemRepository.findByAll(item);
    // 何も記述されていない場合
    // List<Item> itemList = itemRepository.findByPage();
    return itemList;
  }
  
  public List<Item> showAllItems(String name) {
    List<Item> itemList = itemRepository.findByName(name);
    return itemList;
  }

  public List<String> showAllParentCategory() {
    List<String> parentList = categoryRepository.findAllParent().stream().sorted(Comparator.naturalOrder())
        .collect(Collectors.toList());
    return parentList;
  }
  public List<String> showAllChildCategory(String parent) {
    List<String> childList = categoryRepository.findAllChildByParent(parent).stream().sorted(Comparator.naturalOrder())
        .collect(Collectors.toList());
    return childList;
  }
  public List<String> showAllGrandChildCategory(String parent, String child) {
    List<String> childList = categoryRepository.findAllGrandChildByParentAndChild(parent, child).stream().sorted(Comparator.naturalOrder())
        .collect(Collectors.toList());
    return childList;
  }
}