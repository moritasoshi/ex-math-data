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
  private CategoryRepository categoryRepository;
  @Autowired
  private ItemRepository itemRepository;

  private static final int PAGE_SIZE = 30;


  public List<Item> showAllItems(){
  List<Item> itemList = itemRepository.findByPage();
  return itemList;
  }
}