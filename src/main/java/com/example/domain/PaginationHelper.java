package com.example.domain;

import org.springframework.data.domain.Page;

public class PaginationHelper {
  public static Pagination createPagenation(Page pageObject) {
    Pagination pagination = new Pagination();
    pagination.setFirst(pageObject.isFirst());
    pagination.setLast(pageObject.isLast());
    pagination.setNumber(pageObject.getNumber());
    pagination.setNumberOfElements(pageObject.getNumberOfElements());
    pagination.setSize(pageObject.getSize());
    pagination.setTotalElements(pageObject.getTotalElements());
    pagination.setTotalPages(pageObject.getTotalPages());
    pagination.setNumberOfElements(pageObject.getNumberOfElements());
    return pagination;
  }
}