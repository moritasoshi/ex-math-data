package com.example.controller;

import com.example.domain.ItemPage;
import com.example.form.SearchForm;
import com.example.service.MathDataService;

import static java.util.Objects.isNull;

import javax.servlet.http.HttpSession;

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

  @Autowired
  private HttpSession session;

  @ModelAttribute
  public SearchForm setUpSearchForm() {
    return new SearchForm();
  }

  @RequestMapping("")
  public String index(Model model, SearchForm form) {
    Integer page = form.getPage();
    Integer totalPage = (Integer) session.getAttribute("totalPage");
    if (isNull(page) || page <= 0 || isNull(totalPage) || page > totalPage) {
      page = 1;
    }
    ItemPage item = new ItemPage(form.getItemName(), form.getBrand(), form.getParentCategory(), form.getChildCategory(),
        form.getGrandChildCategory(), page);
    ItemPage itemPage = service.showAllItems(item);

    model.addAttribute("searchForm", form);
    model.addAttribute("itemList", itemPage.getItemList());
    model.addAttribute("size", itemPage.getSize());
    model.addAttribute("page", itemPage.getPage());
    model.addAttribute("totalPage", itemPage.getTotalPage()); // 表示用
    session.setAttribute("totalPage", itemPage.getTotalPage()); // サーバー用
    return "list";
  }

}