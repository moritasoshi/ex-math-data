package com.example.repository;

import java.util.List;
import java.util.Objects;

import com.example.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
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

  private final String TABLE_NAME = "items";

  private static final RowMapper<Item> ITE_ROW_MAPPER = (rs, i) -> {
    Item item = new Item();
    item.setId(rs.getInt("id"));
    item.setName(rs.getString("name"));
    item.setCondition(rs.getInt("condition"));
    item.setBrand(rs.getString("brand"));
    item.setPrice(rs.getDouble("price"));
    item.setShipping(rs.getInt("shipping"));
    item.setDescription(rs.getString("description"));
    item.setParent(rs.getString("parent"));
    item.setChild(rs.getString("child"));
    item.setGrandChild(rs.getString("grand_child"));
    return item;
  };

  public Integer save(Item item) {
    Integer itemId = item.getId();
    if (Objects.isNull(itemId)) {
      // 採番idの取得
      itemId = getMaxId() + 1;
      item.setId(itemId);
      String insertSql = "INSERT INTO " + TABLE_NAME
          + "( id ,name ,CONDITION ,category ,brand ,price ,shipping ,description ) VALUES( :id ,:name ,:condition ,( SELECT rel2.descendant_id AS category FROM relations AS rel1 LEFT JOIN relations AS rel2 ON  rel1.descendant_id = rel2.ancestor_id WHERE rel1.ancestor_id < rel1.descendant_id AND rel2.ancestor_id < rel2.descendant_id AND rel1.ancestor_id IN ( SELECT id FROM category WHERE name = :parent AND depth = 1 ) AND rel1.descendant_id IN( SELECT id FROM category WHERE name = :child AND depth = 2 ) AND rel2.descendant_id IN( SELECT id FROM category WHERE name = :grandChild AND depth = 3 ) ) ,:brand ,:price ,:shipping ,:description )";
      SqlParameterSource param = new BeanPropertySqlParameterSource(item);
      template.update(insertSql, param);
      return itemId;
    } else {
      String updateSql = "UPDATE " + TABLE_NAME
          + " SET name = :name, CONDITION = :condition, category = ( SELECT rel2.descendant_id AS grand_child_category_id FROM relations AS rel1 LEFT JOIN relations AS rel2 ON  rel1.descendant_id = rel2.ancestor_id WHERE rel1.ancestor_id < rel1.descendant_id AND rel2.ancestor_id < rel2.descendant_id AND rel1.ancestor_id IN( SELECT id FROM category WHERE name = :parent AND depth = 1 ) AND rel1.descendant_id IN( SELECT id FROM category WHERE name = :child AND depth = 2 ) AND rel2.descendant_id IN( SELECT id FROM category WHERE name = :grandChild AND depth = 3 ) ), brand = :brand, price = :price, description = :description WHERE id = :id";
      SqlParameterSource param = new BeanPropertySqlParameterSource(item);
      template.update(updateSql, param);
      return itemId;
    }
  }

  public Integer getMaxId() {
    String sql = "SELECT MAX(id) FROM " + TABLE_NAME;
    SqlParameterSource param = new MapSqlParameterSource();
    Integer maxId = template.queryForObject(sql, param, Integer.class);
    return maxId;
  }

  public Item loadItem(Integer id) {
    String sql = "SELECT ite.id AS id, ite.name AS name, ite.price AS price, cat1.name AS parent, cat2.name AS child, cat3.name AS grand_child, ite.brand AS brand, ite.CONDITION AS CONDITION, ite.shipping AS shipping, ite.description AS description FROM "
        + TABLE_NAME
        + " AS ite JOIN relations AS rel1 ON  ite.category = rel1.descendant_id JOIN relations AS rel2 ON  rel1.ancestor_id = rel2.descendant_id JOIN category AS cat1 ON  rel2.ancestor_id = cat1.id JOIN category AS cat2 ON  rel1.ancestor_id = cat2.id JOIN category AS cat3 ON  rel1.descendant_id = cat3.id WHERE rel2.ancestor_id < rel1.ancestor_id AND rel1.ancestor_id < rel1.descendant_id AND ite.id = :id";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    List<Item> result = template.query(sql, param, ITE_ROW_MAPPER);
    Item item = new Item();
    try {
      item = result.get(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return item;
  }

}