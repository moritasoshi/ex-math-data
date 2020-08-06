package com.example.controller;

import java.util.List;

import com.example.service.MathDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax")
public class AjaxController {

  @Autowired
  private MathDataService service;
  
  @RequestMapping("/setUpParentCategory")
  public List<String> setUpParentCategory(){
    List<String> parentList = service.showAllParentCategory();
    return parentList;
  }
  
  @RequestMapping("/setUpChildCategory")
  public List<String> setUpChildCategory(String parent){
    List<String> childList = service.showAllChildCategory(parent);
    return childList;
  }
  
  @RequestMapping("/setUpGrandChildCategory")
  public List<String> setUpGrandChildCategory(String parent, String child){
    List<String> childList = service.showAllGrandChildCategory(parent, child);
    return childList;
  }
}