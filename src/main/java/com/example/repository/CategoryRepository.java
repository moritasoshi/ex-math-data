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

  public Integer save(Category category) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(category);
    Number key = insert.executeAndReturnKey(param);
    return key.intValue();
  }
}