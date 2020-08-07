package com.example.form;

import lombok.Data;

@Data
public class SearchForm {
  private String itemName = "";
  private String parentCategory = "";
  private String childCategory = "";
  private String grandChildCategory = "";
  private String brand = "";

  private Integer page = 1;
}