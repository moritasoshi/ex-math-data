package com.example.repository;

import java.util.List;

import com.example.domain.CategoryForView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryForViewRepository {
  @Autowired
  private NamedParameterJdbcTemplate template;
  
  private SimpleJdbcInsert insert;
  private static final RowMapper<CategoryForView> CATFORVIEW_ROW_MAPPER = (rs, i) -> {
    CategoryForView category = new CategoryForView();
    category.setParent(rs.getString("parent"));
    category.setChild(rs.getString("child"));
    category.setGrandChild(rs.getString("grand_child"));
    return category;
  };

  public List<CategoryForView> findAll(){
    String sql = "SELECT cat1.name AS parent, cat2.name AS child, cat3.name AS grand_child FROM relations AS rel1 LEFT JOIN relations AS rel2 ON  rel1.descendant_id = rel2.ancestor_id LEFT JOIN category AS cat1 ON  rel1.ancestor_id = cat1.id LEFT JOIN category AS cat2 ON  rel2.ancestor_id = cat2.id LEFT JOIN category AS cat3 ON  rel2.descendant_id = cat3.id WHERE cat1.depth = 1 AND cat2.depth = 2 AND cat3.depth = 3";
    SqlParameterSource param = new MapSqlParameterSource();
    List<CategoryForView> catList = template.query(sql, param, CATFORVIEW_ROW_MAPPER);
    return catList;
  }

}