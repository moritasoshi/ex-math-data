package com.example.repository;

import com.example.domain.Relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RelationRepository {

  @Autowired
  private NamedParameterJdbcTemplate template;

  public void save(Relation relation) {
    String sql = "INSERT INTO relations(ancestor_id, descendant_id, depth) VALUES (:ancestorId, :descendantId, :depth);";
    SqlParameterSource param = new MapSqlParameterSource().addValue("ancestorId", relation.getAncestorId())
        .addValue("descendantId", relation.getDescendantId());
    template.update(sql, param);
  }

}