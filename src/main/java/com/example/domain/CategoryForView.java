package com.example.domain;

public class CategoryForView {
  private String parent;
  private String child;
  private String grandChild;

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

}