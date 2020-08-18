package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class EditCategoryForm {
  @Pattern(regexp = "\\d{1,9}", message = "The id is incorrect.")
  private String id;
  @NotBlank(message = "error : Please fill in the name")
  private String name;

  public Integer getIntId() {
    return Integer.parseInt(this.id);
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


}