package com.example.form;

import lombok.Data;

@Data
public class EditForm {
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

}