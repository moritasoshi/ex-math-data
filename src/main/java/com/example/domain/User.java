package com.example.domain;

import java.util.Date;

public class User {
  private Integer id;
  private String name;
  private String mailAddress;
  private String password;
  private Integer authority;
  private String uuid;
  private Date registerDate;

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

  public String getMailAddress() {
    return this.mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getAuthority() {
    return this.authority;
  }

  public void setAuthority(Integer authority) {
    this.authority = authority;
  }

  public String getUuid() {
    return this.uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Date getRegisterDate() {
    return this.registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }
}