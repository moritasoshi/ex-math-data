package com.example.repository;

import java.util.List;

import com.example.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  @Autowired
  private NamedParameterJdbcTemplate template;

  private static final RowMapper<User> USE_ROW_MAPPER = (rs, i) -> {
    User user = new User();
    user.setId(rs.getInt("id"));
    user.setName(rs.getString("name"));
    user.setMailAddress(rs.getString("mail_address"));
    user.setPassword(rs.getString("password"));
    user.setAuthority(rs.getInt("authority"));
    user.setUuid(rs.getString("uuid"));
    user.setRegisterDate(rs.getDate("register_date"));
    return user;
  };

  public void save(User user) {
    String insertSql = "INSERT INTO users(name, mail_address, password, authority, uuid, register_date) VALUES(:name, :mailAddress, :password, :authority, :uuid, :registerDate)";
    SqlParameterSource param = new BeanPropertySqlParameterSource(user);
    template.update(insertSql, param);
  }

  public User findByMailAddress(String mailAddress) {
    String sql = "SELECT id, name, mail_address, password, authority, uuid, register_date FROM users WHERE mail_address = :mailAddress";
    SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
    List<User> user = template.query(sql, param, USE_ROW_MAPPER);
    return user.get(0);
  }

}