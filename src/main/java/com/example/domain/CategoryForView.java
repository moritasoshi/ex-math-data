package com.example.domain;

public class CategoryForView {
  private Integer parentId;
  private Integer childId;
  private Integer grandChildId;
  private String parent;
  private String child;
  private String grandChild;

  public Integer getParentId() {
    return this.parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Integer getChildId() {
    return this.childId;
  }

  public void setChildId(Integer childId) {
    this.childId = childId;
  }

  public Integer getGrandChildId() {
    return this.grandChildId;
  }

  public void setGrandChildId(Integer grandChildId) {
    this.grandChildId = grandChildId;
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

}