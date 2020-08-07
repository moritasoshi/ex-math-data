package com.example.domain;

import lombok.Data;

@Data
public class Item {
  private Integer id;
  private String name;
  private Integer condition;
  private String parent;
  private String child;
  private String grandChild;
  private String brand;
  private Double price;
  private Integer shipping;
  private String description;
  public Item(String name, String brand, String parent, String child, String grandChild){
    this.name = name;
    this.brand = brand;
    this.parent = parent;
    this.child = child;
    this.grandChild = grandChild;
  }
  public Item(){
  }
}