package com.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"ID", "Name", "Price", "Parent", "Child", "GrandChild", "Brand", "Condition", "Shipping", "Description"})
public class Item {
  @JsonProperty("ID")
  private Integer id;
  @JsonProperty("Name")
  private String name;
  @JsonProperty("Condition")
  private Integer condition;
  @JsonProperty("Parent")
  private String parent;
  @JsonProperty("Child")
  private String child;
  @JsonProperty("GrandChild")
  private String grandChild;
  @JsonProperty("Brand")
  private String brand;
  @JsonProperty("Price")
  private Double price;
  @JsonProperty("Shipping")
  private Integer shipping;
  @JsonProperty("Description")
  private String description;

  public Item(String name, String brand, String parent, String child, String grandChild) {
    this.name = name;
    this.brand = brand;
    this.parent = parent;
    this.child = child;
    this.grandChild = grandChild;
  }

  public Item() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCondition() {
    return this.condition;
  }

  public void setCondition(Integer condition) {
    this.condition = condition;
  }

  public String getParent() {
    return this.parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getChild() {
    return this.child;
  }

  public void setChild(String child) {
    this.child = child;
  }

  public String getGrandChild() {
    return this.grandChild;
  }

  public void setGrandChild(String grandChild) {
    this.grandChild = grandChild;
  }

  public String getBrand() {
    return this.brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getShipping() {
    return this.shipping;
  }

  public void setShipping(Integer shipping) {
    this.shipping = shipping;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}