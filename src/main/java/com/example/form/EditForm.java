package com.example.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class EditForm {
  @Pattern(regexp = "\\d{1,9}", message = "The id is incorrect.")
  private String id;
  @NotBlank(message = "Please fill in the name")
  private String name;
  private Integer condition;
  @NotBlank(message = "Please select the parent category")
  private String parent;
  @NotBlank(message = "Please select the child category")
  private String child;
  @NotBlank(message = "Please select the grandchild category")
  private String grandChild;
  private String brand;
  @Pattern(regexp = "[1-9]\\d{0,8}.0", message = "Please enter the price in the following format.  Integer: 9 digits or less AND Decimal: 0")
  private String price;
  private Integer shipping;
  private String description;

  public Integer getIntId(){
    return Integer.parseInt(this.id);
  }
  public Double getDouPrice(){
    return Double.parseDouble(this.price);
  }

}