package com.example.controller;

import com.example.domain.Category;
import com.example.domain.CategoryForView;
import com.example.domain.Item;
import com.example.domain.ItemPage;
import com.example.domain.User;
import com.example.form.AddCategoryForm;
import com.example.form.AddItemForm;
import com.example.form.EditCategoryForm;
import com.example.form.EditItemForm;
import com.example.form.SearchForm;
import com.example.form.UserForm;
import com.example.service.MathDataService;

import static java.util.Objects.isNull;

import java.util.List;

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

	////// ユーザー関連
	////////////////////////////////////
	//// ログイン画面
	////////////////////////////////////
	@RequestMapping("/to-login")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/login")
	public String login() {
		return "list";
	}

	////////////////////////////////////
	//// ユーザー登録
	////////////////////////////////////
	@RequestMapping("/to-register")
	public String toRegister(Model model) {
		UserForm form = new UserForm();
		model.addAttribute("userForm", form);
		return "register";
	}

	@RequestMapping("/register")
	public String register(@Validated UserForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("userForm", form);
			return "register";
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		service.saveUser(user);
		return "redirect:/to-login";
	}

	////// Item関連
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

	////// Category関連
	////////////////////////////////////
	//// カテゴリー一覧の表示
	////////////////////////////////////
	@RequestMapping("/category")
	public String category(Model model) {
		List<CategoryForView> catList = service.showAllCategory();
		model.addAttribute("catList", catList);
		return "list_category";
	}

	////////////////////////////////////
	//// カテゴリー詳細の表示
	////////////////////////////////////
	@RequestMapping("/category/detail")
	public String categoryDetail(Integer id, Model model) {
		Category category = service.showCategory(id);
		model.addAttribute("category", category);
		return "detail_category";
	}

	////////////////////////////////////
	//// カテゴリーの編集
	////////////////////////////////////
	@RequestMapping("/category/to-edit")
	public String categoryToEdit(Integer id, Model model) {
		EditCategoryForm form = new EditCategoryForm();
		Category category = service.showCategory(id);
		BeanUtils.copyProperties(category, form);
		form.setId(category.getId().toString());
		model.addAttribute("editCategoryForm", form);
		return "edit_category";
	}

	@RequestMapping("/category/edit")
	public String categoryEdit(@Validated EditCategoryForm form, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editCategoryForm", form);
			return "edit_category";
		}
		Category category = new Category();
		BeanUtils.copyProperties(form, category);
		category.setId(form.getIntId());
		Integer categoryId = service.saveCategory(category);
		redirectAttributes.addAttribute("id", categoryId);
		return "redirect:/category/detail/?id={id}";
	}

	////////////////////////////////////
	//// カテゴリーの追加
	////////////////////////////////////
	@RequestMapping("/category/to-add")
	public String toAddCategory(Model model) {
		AddCategoryForm form = new AddCategoryForm();
		model.addAttribute("addCategoryForm", form);
		return "add_category";
	}

	@RequestMapping("/category/add")
	public String addCategory(@Validated AddCategoryForm form, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("addCategoryForm", form);
			return "add_category";
		}
		return "redirect:/category";
	}

}