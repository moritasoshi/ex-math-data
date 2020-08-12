package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class EditItemForm {
  @Pattern(regexp = "\\d{1,9}", message = "The id is incorrect.")
  private String id;
  @NotBlank(message = "error : Please fill in the name")
  private String name;
  @NotBlank(message = "error : Please select the condition")
  private String condition;
  @NotBlank(message = "error : Please select the parent category")
  private String parent;
  @NotBlank(message = "error : Please select the child category")
  private String child;
  @NotBlank(message = "error : Please select the grandchild category")
  private String grandChild;
  private String brand;
  @Pattern(regexp = "[1-9]\\d{0,8}.0", message = "error : Please enter the price in the following format.  Integer: 9 digits or less AND Decimal: 0")
  private String price;
  private Integer shipping;
  private String description;

  public Integer getIntId() {
    return Integer.parseInt(this.id);
  }

  public Integer getIntCondition() {
    return Integer.parseInt(this.condition);
  }

  public Double getDouPrice() {
    return Double.parseDouble(this.price);
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCondition() {
    return this.condition;
  }

  public void setCondition(String condition) {
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

  public String getPrice() {
    return this.price;
  }

  public void setPrice(String price) {
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