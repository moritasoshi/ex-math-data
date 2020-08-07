package com.example.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    String sql = "INSERT INTO items VALUES (:id, :name, :condition, :category, :brand, :price, :shipping, :description)";
    SqlParameterSource param = new BeanPropertySqlParameterSource(item);
    Integer count = template.update(sql, param);
    return count;
  }

  public List<Item> findByPage() {
    String sql = "SELECT ite.id AS id, ite.name AS name, ite.price AS price, ( SELECT name FROM category WHERE id = rel2.ancestor_id ) AS parent, ( SELECT name FROM category WHERE id = rel1.ancestor_id ) AS child, ( SELECT name FROM category WHERE id = rel1.descendant_id ) AS grand_child, ite.brand AS brand, ite.condition AS condition, ite.shipping AS shipping, ite.description AS description FROM items AS ite JOIN relations AS rel1 ON  ite.category = rel1.descendant_id JOIN relations AS rel2 ON  rel1.ancestor_id = rel2.descendant_id WHERE rel2.ancestor_id < rel1.ancestor_id AND rel1.ancestor_id < rel1.descendant_id ORDER BY ite.id LIMIT 30 OFFSET 0";
    SqlParameterSource param = new MapSqlParameterSource();
    List<Item> itemList = template.query(sql, param, ITE_ROW_MAPPER);
    return itemList;
  }

  public List<Item> findByName(String name) {
    String sql = "SELECT ite.id AS id, ite.name AS name, ite.price AS price, ( SELECT name FROM category WHERE id = rel2.ancestor_id ) AS parent, ( SELECT name FROM category WHERE id = rel1.ancestor_id ) AS child, ( SELECT name FROM category WHERE id = rel1.descendant_id ) AS grand_child, ite.brand AS brand, ite.condition AS condition, ite.shipping AS shipping, ite.description AS description FROM items AS ite JOIN relations AS rel1 ON  ite.category = rel1.descendant_id JOIN relations AS rel2 ON  rel1.ancestor_id = rel2.descendant_id WHERE rel2.ancestor_id < rel1.ancestor_id AND rel1.ancestor_id < rel1.descendant_id AND ite.name LIKE :name ORDER BY ite.id LIMIT 30 OFFSET 0";
    SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
    List<Item> itemList = template.query(sql, param, ITE_ROW_MAPPER);
    return itemList;
  }

  public List<Item> findByAll(Item item) {
    StringBuilder sql = new StringBuilder();
    Map<String, String> paramMap = new HashMap<>();
    String name = item.getName();
    String brand = item.getBrand();
    String parent = item.getParent();
    String child = item.getChild();
    String grandChild = item.getGrandChild();

    String nameSql = "AND ite.name ILIKE :name ";
    String brandSql = "AND ite.brand ILIKE :brand ";
    String parentSql = "AND cat1.name LIKE :parent ";
    String childSql = "AND cat2.name LIKE :child ";
    String grandChildSql = "AND cat3.name LIKE :grandChild ";

    // name
    paramMap.put("name", "%" + name + "%");

    // brand
    if ("".equals(brand)) {
      brandSql = ""; // brand指定がない場合はWHERE条件から排除
    } else {
      paramMap.put("brand", "%" + brand + "%");
    }
    // parent
    if ("".equals(parent)) {
      paramMap.put("parent", "%");
    } else {
      paramMap.put("parent", parent);
    }
    // child
    if ("".equals(child)) {
      paramMap.put("child", "%");
    } else {
      paramMap.put("child", child);
    }
    // grandChild
    if ("".equals(grandChild)) {
      paramMap.put("grandChild", "%");
    } else {
      paramMap.put("grandChild", grandChild);
    }

    // SQL文の生成
    sql.append(
      "SELECT ite.id AS id, ite.name AS name, ite.price AS price, cat1.name AS parent, cat2.name AS child, cat3.name AS grand_child, ite.brand AS brand, ite.CONDITION AS CONDITION, ite.shipping AS shipping, ite.description AS description FROM items AS ite JOIN relations AS rel1 ON  ite.category = rel1.descendant_id JOIN relations AS rel2 ON  rel1.ancestor_id = rel2.descendant_id JOIN category AS cat1 ON  rel2.ancestor_id = cat1.id JOIN category AS cat2 ON  rel1.ancestor_id = cat2.id JOIN category AS cat3 ON  rel1.descendant_id = cat3.id WHERE rel2.ancestor_id < rel1.ancestor_id AND rel1.ancestor_id < rel1.descendant_id ");
    sql.append(nameSql);
    sql.append(brandSql);
    sql.append(parentSql);
    sql.append(childSql);
    sql.append(grandChildSql);

    sql.append("ORDER BY ite.id LIMIT 30 OFFSET 0");
    List<Item> itemList = template.query(sql.toString(), paramMap, ITE_ROW_MAPPER);
    return itemList;
  }
}