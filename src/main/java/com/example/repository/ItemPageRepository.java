
package com.example.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ItemPageRepository {
  @Autowired
  private NamedParameterJdbcTemplate template;

  private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
    Item Item = new Item();
    Item.setId(rs.getInt("id"));
    Item.setName(rs.getString("name"));
    Item.setCondition(rs.getInt("condition"));
    Item.setBrand(rs.getString("brand"));
    Item.setPrice(rs.getDouble("price"));
    Item.setShipping(rs.getInt("shipping"));
    Item.setDescription(rs.getString("description"));
    Item.setParent(rs.getString("parent"));
    Item.setChild(rs.getString("child"));
    Item.setGrandChild(rs.getString("grand_child"));
    return Item;
  };

  public Integer save(Item item) {
    String sql = "INSERT INTO items VALUES (:id, :name, :condition, :category, :brand, :price, :shipping, :description)";
    SqlParameterSource param = new BeanPropertySqlParameterSource(item);
    Integer count = template.update(sql, param);
    return count;
  }

  public ItemPage findByAll(ItemPage itemPage) {
    StringBuilder sql = new StringBuilder();
    StringBuilder sizeSql = new StringBuilder();
    Map<String, Object> paramMap = new HashMap<>();
    String name = itemPage.getName();
    String brand = itemPage.getBrand();
    String parent = itemPage.getParent();
    String child = itemPage.getChild();
    String grandChild = itemPage.getGrandChild();
    Integer page = itemPage.getPage();
    Integer limit = itemPage.getPageSize();
    Integer offset = limit * (page - 1);

    String selectSql = "SELECT ite.id AS id, ite.name AS name, ite.price AS price, cat1.name AS parent, cat2.name AS child, cat3.name AS grand_child, ite.brand AS brand, ite.CONDITION AS CONDITION, ite.shipping AS shipping, ite.description AS description ";
    String fromSql = "FROM items AS ite JOIN relations AS rel1 ON  ite.category = rel1.descendant_id JOIN relations AS rel2 ON  rel1.ancestor_id = rel2.descendant_id JOIN category AS cat1 ON  rel2.ancestor_id = cat1.id JOIN category AS cat2 ON  rel1.ancestor_id = cat2.id JOIN category AS cat3 ON  rel1.descendant_id = cat3.id WHERE rel2.ancestor_id < rel1.ancestor_id AND rel1.ancestor_id < rel1.descendant_id ";
    String nameSql = "AND ite.name ILIKE :name ";
    String brandSql = "AND ite.brand ILIKE :brand ";
    String parentSql = "AND cat1.name LIKE :parent ";
    String childSql = "AND cat2.name LIKE :child ";
    String grandChildSql = "AND cat3.name LIKE :grandChild ";
    String orderSql = "ORDER BY ite.id LIMIT :limit OFFSET :offset";

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
    // LIMIT OFFSET
    paramMap.put("limit", limit);
    paramMap.put("offset", offset);

    // SQL文の生成
    sql.append(selectSql);
    sql.append(fromSql);
    sql.append(nameSql);
    sql.append(brandSql);
    sql.append(parentSql);
    sql.append(childSql);
    sql.append(grandChildSql);
    sql.append(orderSql);
    List<Item> itemList = template.query(sql.toString(), paramMap, ITEM_ROW_MAPPER);

    // 検索条件にマッチする商品数を知りたい
    selectSql = "SELECT COUNT(*) ";
    sizeSql.append(selectSql);
    sizeSql.append(fromSql);
    sizeSql.append(nameSql);
    sizeSql.append(brandSql);
    sizeSql.append(parentSql);
    sizeSql.append(childSql);
    sizeSql.append(grandChildSql);
    Integer size = template.queryForObject(sizeSql.toString(), paramMap, Integer.class);

    ItemPage result = new ItemPage();
    result.setItemList(itemList);
    result.setSize(size);
    result.setPage(page);
    result.setTotalPage(size / limit + 1);
    return result;
  }
}