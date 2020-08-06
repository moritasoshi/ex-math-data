package com.example.controller;

import java.util.List;
import java.util.Objects;

import com.example.domain.Item;
import com.example.service.MathDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MathDataController {
  @Autowired
  private MathDataService service;

  @RequestMapping("")
  public String index(Integer page, Model model) {
    // Integer totalPages = service.showAllbyPage(1).getTotalPages();
    // if (Objects.isNull(page) || page < 0 || page > totalPages) {
    //   page = 0;
    // }
    // Page<Item> itemPage = service.showAllbyPage(page);
    List<Item> itemList = service.showAllItems();
    model.addAttribute("page", page);
    model.addAttribute("itemList", itemList);
    // model.addAttribute("totalPages", totalPages);
    return "list";
  }

}