package com.example.service;

import java.util.List;

import com.example.domain.*;
import com.example.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private UserRepository userRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private CategoryForViewRepository categoryForViewRepository;

  @Autowired
	private PasswordEncoder passwordEncoder;

  private static final int PAGE_SIZE = 30;

  public Item showDetail(Integer id) {
    return itemRepository.loadItem(id);
  }

  public Integer saveItem(Item item) {
    return itemRepository.save(item);
  }

  public void saveUser(User user) {
    String inputPassword = user.getPassword();
		user.setPassword(passwordEncoder.encode(inputPassword));
    userRepository.save(user);
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

  public List<CategoryForView> showAllCategory(){
    return categoryForViewRepository.findAll();
  }
}