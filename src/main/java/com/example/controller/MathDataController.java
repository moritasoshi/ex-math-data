package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.service.MathDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MathDataController {
  @Autowired
  private MathDataService service;

  @ModelAttribute
  public SearchForm setUpSearchForm() {
    return new SearchForm();
  }

  @RequestMapping("")
  public String index(Model model, SearchForm form) {
    List<Item> itemList;
    Item item = new Item(form.getItemName(), form.getBrand(), form.getParentCategory(), form.getChildCategory(),
        form.getGrandChildCategory());
    itemList = service.showAllItems(item);
    // if (Objects.isNull(form.getItemName()) || "".equals(form.getItemName())) {
    // itemList = service.showAllItems();
    // } else {
    // itemList = service.showAllItems(form.getItemName());
    // }
    model.addAttribute("searchForm", form);
    model.addAttribute("itemList", itemList);
    return "list";
  }

}