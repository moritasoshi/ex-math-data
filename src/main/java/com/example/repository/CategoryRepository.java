package com.example.repository;

import java.util.List;

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
  private static final RowMapper<Category> CAT_ROW_MAPPER = (rs, i) -> {
    Category category = new Category();
    category.setId(rs.getInt("id"));
    category.setName(rs.getString("name"));
    category.setNameAll(rs.getString("name_all"));
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

  public Integer save(Category category) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(category);
    Number key = insert.executeAndReturnKey(param);
    return key.intValue();
  }
}