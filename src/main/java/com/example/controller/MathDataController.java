package com.example.controller;

import com.example.domain.Item;
import com.example.domain.ItemPage;
import com.example.form.AddItemForm;
import com.example.form.EditItemForm;
import com.example.form.SearchForm;
import com.example.service.MathDataService;

import static java.util.Objects.isNull;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	////////////////////////////////////
	//// 商品一覧画面の表示＆検索機能
	////////////////////////////////////

	@RequestMapping("")
	public String index(Model model, SearchForm form) {
		// 不正な値のチェック
		Integer page = form.getPage();
		Integer totalPage = (Integer) session.getAttribute("totalPage");
		if (isNull(page) || page <= 0 || isNull(totalPage)) {
			page = 1;
		} else if (page > totalPage) {
			page = totalPage;
		}

		// 検索条件の設定
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

	////////////////////////////////////
	//// 商品追加画面の表示&商品の追加
	////////////////////////////////////
	@RequestMapping("/to-add")
	public String toAdd(Model model) {
		AddItemForm form = new AddItemForm();
		model.addAttribute("addItemForm", form);
		return "add";
	}

	@RequestMapping("/add")
	public String add(@Validated AddItemForm form, BindingResult result, RedirectAttributes redirectAttributes,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("addItemForm", form);
			return "add";
		}
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setPrice(form.getDouPrice());
		item.setCondition(form.getIntCondition());
		Integer itemId = service.saveItem(item);
		redirectAttributes.addAttribute("id", itemId);
		return "redirect:/detail/?id={id}";
	}

	////////////////////////////////////
	//// 商品詳細画面の表示
	////////////////////////////////////
	@RequestMapping("/detail")
	public String detail(Integer id, Model model) {
		Item item = service.showDetail(id);
		model.addAttribute("item", item);
		return "detail";
	}

	////////////////////////////////////
	//// 商品編集画面の表示&商品の変更
	////////////////////////////////////
	@RequestMapping("/to-edit")
	public String toEdit(Integer id, Model model) {
		EditItemForm form = new EditItemForm();
		Item item = service.showDetail(id);
		BeanUtils.copyProperties(item, form);
		form.setId(item.getId().toString());
		form.setPrice(item.getPrice().toString());
		form.setCondition(item.getCondition().toString());
		model.addAttribute("editItemForm", form);
		return "edit";
	}

	@RequestMapping("/edit")
	public String edit(@Validated EditItemForm form, BindingResult result, RedirectAttributes redirectAttributes,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editItemForm", form);
			return "edit";
		}
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setId(form.getIntId());
		item.setPrice(form.getDouPrice());
		item.setCondition(form.getIntCondition());
		Integer itemId = service.saveItem(item);
		redirectAttributes.addAttribute("id", itemId);
		return "redirect:/detail/?id={id}";
	}
}