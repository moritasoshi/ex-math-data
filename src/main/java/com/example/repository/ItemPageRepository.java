
package com.example.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ItemPageRepository {
  @Autowired
  private NamedParameterJdbcTemplate template;

  private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
    final Item item = new Item();
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

  public ItemPage findByAll(final ItemPage itemPage) {
    final StringBuilder sql = new StringBuilder();
    final StringBuilder sizeSql = new StringBuilder();
    final Map<String, Object> paramMap = new HashMap<>();
    final String name = itemPage.getName();
    final String brand = itemPage.getBrand();
    final String parent = itemPage.getParent();
    final String child = itemPage.getChild();
    final String grandChild = itemPage.getGrandChild();
    final Integer page = itemPage.getPage();
    final Integer limit = itemPage.getPageSize();
    final Integer offset = limit * (page - 1);

    String selectSql = "SELECT ite.id AS id, ite.name AS name, ite.price AS price, cat1.name AS parent, cat2.name AS child, cat3.name AS grand_child, ite.brand AS brand, ite.CONDITION AS CONDITION, ite.shipping AS shipping, ite.description AS description ";
    final String fromSql = "FROM test_items AS ite LEFT JOIN relations AS rel1 ON  ite.category = rel1.descendant_id LEFT JOIN relations AS rel2 ON  rel1.ancestor_id = rel2.descendant_id LEFT JOIN category AS cat1 ON  rel2.ancestor_id = cat1.id LEFT JOIN category AS cat2 ON  rel1.ancestor_id = cat2.id LEFT JOIN category AS cat3 ON  rel1.descendant_id = cat3.id ";
    final String nameSql = "WHERE ite.name ILIKE :name ";
    String brandSql = "AND ite.brand ILIKE :brand ";
    String parentSql = "AND (cat1.id ISNULL OR (cat1.depth=1 AND cat1.name LIKE :parent)) ";
    String childSql = "AND (cat2.id ISNULL OR (cat2.depth=2 AND cat2.name LIKE :child)) ";
    String grandChildSql = "AND (cat3.id ISNULL OR (cat3.depth=3 AND cat3.name LIKE :grandChild)) ";
    final String orderSql = "ORDER BY ite.id LIMIT :limit OFFSET :offset";

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
      parentSql = "AND (cat1.depth=1 AND  cat1.name LIKE :parent) ";
      paramMap.put("parent", parent);
    }
    // child
    if ("".equals(child)) {
      paramMap.put("child", "%");
    } else {
      childSql = "AND (cat2.depth=2 AND  cat2.name LIKE :child) ";
      paramMap.put("child", child);
    }
    // grandChild
    if ("".equals(grandChild)) {
      paramMap.put("grandChild", "%");
    } else {
      grandChildSql = "AND (cat3.depth=3 AND  cat3.name LIKE :grandChild) ";
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
    final List<Item> itemList = template.query(sql.toString(), paramMap, ITEM_ROW_MAPPER);

    // 検索条件にマッチする商品数を知りたい
    selectSql = "SELECT COUNT(*) ";
    sizeSql.append(selectSql);
    sizeSql.append(fromSql);
    sizeSql.append(nameSql);
    sizeSql.append(brandSql);
    sizeSql.append(parentSql);
    sizeSql.append(childSql);
    sizeSql.append(grandChildSql);
    final Integer size = template.queryForObject(sizeSql.toString(), paramMap, Integer.class);

    final ItemPage result = new ItemPage();
    result.setItemList(itemList);
    result.setSize(size);
    result.setPage(page);
    result.setTotalPage(size / limit + 1);
    return result;
  }
}