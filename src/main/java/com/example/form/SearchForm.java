package com.example.form;

public class SearchForm {
  private String itemName = "";
  private String parentCategory = "";
  private String childCategory = "";
  private String grandChildCategory = "";
  private String brand = "";
  private Integer page = 1;

  public String getItemName() {
    return this.itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getParentCategory() {
    return this.parentCategory;
  }

  public void setParentCategory(String parentCategory) {
    this.parentCategory = parentCategory;
  }

  public String getChildCategory() {
    return this.childCategory;
  }

  public void setChildCategory(String childCategory) {
    this.childCategory = childCategory;
  }

  public String getGrandChildCategory() {
    return this.grandChildCategory;
  }

  public void setGrandChildCategory(String grandChildCategory) {
    this.grandChildCategory = grandChildCategory;
  }

  public String getBrand() {
    return this.brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Integer getPage() {
    return this.page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

}