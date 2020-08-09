package com.example.service;

import java.util.List;

import com.example.domain.*;
import com.example.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MathDataService {

  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private ItemPageRepository itemPageRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  private static final int PAGE_SIZE = 30;

  public Item showDetail(Integer id) {
    return itemRepository.loadItem(id);
  }

  public void saveItem(Item item){
    itemRepository.save(item);
  }

  public ItemPage showAllItems(ItemPage itemPage) {
    itemPage.setPageSize(PAGE_SIZE);
    return itemPageRepository.findByAll(itemPage);
  }

  public List<String> showAllParentCategory() {
    return categoryRepository.findAllParent();
  }

  public List<String> showAllChildCategory(String parent) {
    return categoryRepository.findAllChildByParent(parent);
  }

  public List<String> showAllGrandChildCategory(String parent, String child) {
    return categoryRepository.findAllGrandChildByParentAndChild(parent, child);
  }
}