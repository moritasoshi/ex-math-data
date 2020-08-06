package com.example.repository;

import com.example.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
  @Autowired
  private NamedParameterJdbcTemplate template;

  private static final RowMapper<Item> ITE_ROW_MAPPER = (rs, i) -> {
    Item item = new Item();
    item.setId(rs.getInt("id"));
    item.setName(rs.getString("name"));
    item.setCondition(rs.getInt("condition"));
    item.setCategory(rs.getInt("category"));
    item.setBrand(rs.getString("brand"));
    item.setPrice(rs.getInt("price"));
    item.setShipping(rs.getInt("shipping"));
    item.setDescription(rs.getString("description"));
    return item;
  };

  public Integer save(Item item) {
    String sql = "INSERT INTO items VALUES (:id, :name, :condition, :category, :brand, :price, :shipping, :description)";
    SqlParameterSource param = new BeanPropertySqlParameterSource(item);
    Integer count = template.update(sql, param);
    return count;
  }

}