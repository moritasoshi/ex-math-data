package com.example.service;

import java.util.List;
import java.util.Objects;

import com.example.common.LogUtils;
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

  public Category showCategory(Integer id) {
    return categoryRepository.loadCategory(id);
  }

  public Integer saveItem(Item item) {
    Integer id = itemRepository.save(item);
    LogUtils.info("Item", id, item.getName()); // ログ出力
    return id;
  }

  public Integer saveCategory(Category category) {
    Integer id;
    String parent = category.getParent();
    String child = category.getChild();
    if (Objects.isNull(category.getId())) {
      if ("".equals(parent) && "".equals(child)) { // 親子なし
        category.setDepth(1);
        id = categoryRepository.saveParent(category);
      } else if (!"".equals(parent) && "".equals(child)) { // 親あり子なし
        category.setDepth(2);
        id = categoryRepository.saveChild(category);
      } else if (!"".equals(parent) && !"".equals(child)) { // 親あり子あり
        category.setDepth(3);
        id = categoryRepository.saveGrandChild(category);
      } else {
        throw new NullPointerException("不正な値が含まれています");
      }
    } else {
      id = categoryRepository.saveChild(category);
    }
    LogUtils.info("Category", id, category.getName()); // ログ出力
    return id;
  }

  public void saveUser(User user) {
    String inputPassword = user.getPassword();
    user.setPassword(passwordEncoder.encode(inputPassword));
    userRepository.save(user);
  }

  public void deleteCategory(Category category) {
    categoryRepository.delete(category);
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

  public List<CategoryForView> showAllCategory() {
    return categoryForViewRepository.findAll();
  }
  public List<Item> showAllItem() {
    return itemPageRepository.findAll();
  }
}