package com.example.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
    item.setBrand(rs.getString("brand"));
    item.setPrice(rs.getDouble("price"));
    item.setShipping(rs.getInt("shipping"));
    item.setDescription(rs.getString("description"));
    Map<String, String> categoryPath = new LinkedHashMap<>();
    categoryPath.put("parent", rs.getString("parent"));
    categoryPath.put("child", rs.getString("child"));
    categoryPath.put("grandChild", rs.getString("grand_child"));
    item.setCategoryPath(categoryPath);
    return item;
  };

  public Integer save(Item item) {
    String sql = "INSERT INTO items VALUES (:id, :name, :condition, :category, :brand, :price, :shipping, :description)";
    SqlParameterSource param = new BeanPropertySqlParameterSource(item);
    Integer count = template.update(sql, param);
    return count;
  }

  public List<Item> findByPage(){
    String sql = "SELECT ite.id AS id, ite.name AS name, ite.price AS price, ( SELECT name FROM category WHERE id = rel2.ancestor_id ) AS parent, ( SELECT name FROM category WHERE id = rel1.ancestor_id ) AS child, ( SELECT name FROM category WHERE id = rel1.descendant_id ) AS grand_child, ite.brand AS brand, ite.condition AS condition, ite.shipping AS shipping, ite.description AS description FROM items AS ite JOIN relations AS rel1 ON  ite.category = rel1.descendant_id JOIN relations AS rel2 ON  rel1.ancestor_id = rel2.descendant_id WHERE rel2.ancestor_id < rel1.ancestor_id AND rel1.ancestor_id < rel1.descendant_id ORDER BY ite.id LIMIT 30 OFFSET 0";
    SqlParameterSource param = new MapSqlParameterSource();
    List<Item> itemList = template.query(sql, param, ITE_ROW_MAPPER);
    return itemList;
  }

}