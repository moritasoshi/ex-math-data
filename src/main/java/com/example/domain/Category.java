package com.example.domain;

public class Category {
  private Integer id;
  private String name;
  private Integer depth;

  private String nameAll;
  private String parent;
  private String child;

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

  public Integer getDepth() {
    return this.depth;
  }

  public void setDepth(Integer depth) {
    this.depth = depth;
  }

  public String getNameAll() {
    return this.nameAll;
  }

  public void setNameAll(String nameAll) {
    this.nameAll = nameAll;
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