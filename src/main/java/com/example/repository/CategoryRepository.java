package com.example.repository;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import com.example.domain.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {
  @Autowired
  private NamedParameterJdbcTemplate template;

  private SimpleJdbcInsert insert;
  private static final String TABLE_NAME = "category";
  private static final RowMapper<Category> CAT_ROW_MAPPER = (rs, i) -> {
    Category category = new Category();
    category.setId(rs.getInt("id"));
    category.setName(rs.getString("name"));
    category.setDepth(rs.getInt("depth"));
    return category;
  };

  @PostConstruct
  public void init() {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
    SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("category");
    insert = withTableName.usingGeneratedKeyColumns("id");
  }

  public List<Category> findAll() {
    String sql = "SELECT * FROM category ORDER BY id";
    SqlParameterSource param = new MapSqlParameterSource();
    List<Category> catList = template.query(sql, param, CAT_ROW_MAPPER);
    return catList;
  }

  public Category loadCategory(Integer id) {
    String sql = "SELECT * FROM category WHERE id=:id";
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    List<Category> catList = template.query(sql, param, CAT_ROW_MAPPER);
    if (catList.size() == 0) {
      return null;
    }
    return catList.get(0);
  }

  public List<String> findAllParent() {
    String sql = "SELECT name FROM category WHERE depth = 1 ORDER BY name";
    SqlParameterSource param = new MapSqlParameterSource();
    List<String> parentList = template.queryForList(sql, param, String.class);
    return parentList;
  }

  public List<String> findAllChildByParent(String parent) {
    String sql = "SELECT cat.name as name FROM category AS cat JOIN relations AS rel ON  cat.id = rel.descendant_id WHERE rel.ancestor_id = ( SELECT id FROM category WHERE name = :parent  AND depth = 1) AND cat.depth = 2";
    SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent);
    List<String> parentList = template.queryForList(sql, param, String.class);
    return parentList;
  }

  public List<String> findAllGrandChildByParentAndChild(String parent, String child) {
    String sql = "SELECT cat.name FROM relations AS rel1 JOIN relations AS rel2 ON  rel1.descendant_id = rel2.ancestor_id JOIN category AS cat ON  cat.id = rel2.descendant_id WHERE rel1.ancestor_id IN( SELECT id FROM category WHERE name = :parent AND depth = 1 ) AND rel1.descendant_id IN( SELECT id FROM category WHERE name = :child AND depth = 2 ) AND cat.depth = 3";
    SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent).addValue("child", child);
    List<String> parentList = template.queryForList(sql, param, String.class);
    return parentList;
  }

  public Integer saveGrandChild(Category category) {
    Integer categoryId = category.getId();
    if (Objects.isNull(categoryId)) {
      // 採番idの取得
      categoryId = getMaxId() + 1;

      // categoryテーブルへの挿入
      category.setId(categoryId);
      String insertSql = "INSERT INTO " + TABLE_NAME + "( id, name, depth ) VALUES( :id, :name, :depth )";
      SqlParameterSource param = new BeanPropertySqlParameterSource(category);
      template.update(insertSql, param);

      // relationsテーブルへの挿入
      // 親-孫
      String a = "INSERT INTO relations( ancestor_id, descendant_id ) VALUES( ( SELECT id FROM category WHERE name = :parent AND depth = 1 ), :id )";
      // 子-孫
      String b = "INSERT INTO relations( ancestor_id, descendant_id ) VALUES( ( SELECT cat2.id AS child_id FROM relations AS rel JOIN category AS cat1 ON  rel.ancestor_id = cat1.id JOIN category AS cat2 ON  rel.descendant_id = cat2.id WHERE cat1.name = :parent AND cat2.name = :child AND cat1.depth = 1 AND cat2.depth = 2 ), :id )";
      // 孫孫
      String c = "INSERT INTO relations( ancestor_id, descendant_id ) VALUES( :id, :id )";
      template.update(a, param);
      template.update(b, param);
      template.update(c, param);
      return categoryId;
    } else {
      String updateSql = "UPDATE " + TABLE_NAME + " SET name = :name WHERE id = :id";
      SqlParameterSource param = new BeanPropertySqlParameterSource(category);
      template.update(updateSql, param);
      return categoryId;
    }
  }

  public Integer saveChild(Category category) {
    Integer categoryId = category.getId();
    if (Objects.isNull(categoryId)) {
      // 採番idの取得
      categoryId = getMaxId() + 1;

      // categoryテーブルへの挿入
      category.setId(categoryId);
      String insertSql = "INSERT INTO " + TABLE_NAME + "( id, name, depth ) VALUES( :id, :name, :depth )";
      SqlParameterSource param = new BeanPropertySqlParameterSource(category);
      template.update(insertSql, param);

      // relationsテーブルへの挿入
      // 親-子
      String a = "INSERT INTO relations( ancestor_id, descendant_id ) VALUES( ( SELECT id FROM category WHERE name = :parent AND depth = 1 ), :id )";
      // 子子
      String c = "INSERT INTO relations( ancestor_id, descendant_id ) VALUES( :id, :id )";
      template.update(a, param);
      template.update(c, param);
      return categoryId;
    } else {
      String updateSql = "UPDATE " + TABLE_NAME + " SET name = :name WHERE id = :id";
      SqlParameterSource param = new BeanPropertySqlParameterSource(category);
      template.update(updateSql, param);
      return categoryId;
    }
  }

  public Integer saveParent(Category category) {
    Integer categoryId = category.getId();
    if (Objects.isNull(categoryId)) {
      // 採番idの取得
      categoryId = getMaxId() + 1;

      // categoryテーブルへの挿入
      category.setId(categoryId);
      String insertSql = "INSERT INTO " + TABLE_NAME + "( id, name, depth ) VALUES( :id, :name, :depth )";
      SqlParameterSource param = new BeanPropertySqlParameterSource(category);
      template.update(insertSql, param);

      // relationsテーブルへの挿入
      // 親親
      String c = "INSERT INTO relations( ancestor_id, descendant_id ) VALUES( :id, :id )";
      template.update(c, param);
      return categoryId;
    } else {
      String updateSql = "UPDATE " + TABLE_NAME + " SET name = :name WHERE id = :id";
      SqlParameterSource param = new BeanPropertySqlParameterSource(category);
      template.update(updateSql, param);
      return categoryId;
    }
  }

  public Integer getMaxId() {
    String sql = "SELECT MAX(id) FROM " + TABLE_NAME;
    SqlParameterSource param = new MapSqlParameterSource();
    Integer maxId = template.queryForObject(sql, param, Integer.class);
    return maxId;
  }
}