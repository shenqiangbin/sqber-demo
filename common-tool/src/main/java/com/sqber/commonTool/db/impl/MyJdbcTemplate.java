package com.sqber.commonTool.db.impl;

import com.sqber.commonTool.db.MyJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class MyJdbcTemplate implements MyJdbc {

    private JdbcTemplate jdbcTemplate;

    public MyJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public <T> List<T> query(Class<T> type, String sql, @Nullable Object... args) {
        return jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(type));
    }

    @Override
    public List<Map<String, Object>> queryForMap(String sql, @Nullable Object... args) {
        return jdbcTemplate.queryForList(sql, args);
    }

}
