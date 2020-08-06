package com.example.domain;

public class Relation {
  private Integer id;
  private Integer ancestorId;
  private Integer descendantId;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAncestorId() {
    return this.ancestorId;
  }

  public void setAncestorId(Integer ancestorId) {
    this.ancestorId = ancestorId;
  }

  public Integer getDescendantId() {
    return this.descendantId;
  }

  public void setDescendantId(Integer descendantId) {
    this.descendantId = descendantId;
  }

}