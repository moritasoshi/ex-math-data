package com.example.form;

import javax.validation.constraints.NotBlank;

public class AddCategoryForm {
  @NotBlank(message = "error : Please fill in the name")
  private String name;
  private String parent;
  private String child;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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

}