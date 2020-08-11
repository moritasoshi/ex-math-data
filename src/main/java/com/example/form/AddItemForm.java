package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class AddItemForm {
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

  public Integer getIntCondition(){
    return Integer.parseInt(this.condition);
  }
  public Double getDouPrice(){
    return Double.parseDouble(this.price);
  }
  
}