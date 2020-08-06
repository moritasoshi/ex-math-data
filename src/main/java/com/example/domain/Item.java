package com.example.domain;

import java.util.Map;

import lombok.Data;

@Data
public class Item {
  private Integer id;
  private String name;
  private Integer condition;
  private Integer category;
  private Map<String, String> categoryPath;
  private String brand;
  private Double price;
  private Integer shipping;
  private String description;
}