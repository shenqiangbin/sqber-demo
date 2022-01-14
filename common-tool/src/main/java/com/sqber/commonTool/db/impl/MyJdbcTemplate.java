package com.sqber.commonTool.db.impl;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.model.PageModel;
import com.sqber.commonTool.db.sql.SqlWoker;
import com.sqber.commonTool.db.sql.SqlWorkerFactory;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

    // 这个根据不同的库进行下优化，创建 分页的 sql 和 countsql 则不同。
//    @Override
//    public <T> PageModel<T> pageQuery(Class<T> type, String sql, String sumSql, int pageIndex, int pageSize, @Nullable Object... args) {
//
//        int startIndex = (pageIndex - 1) * pageSize;
//        String querySql = String.format("%s limit %s,%s", sql, startIndex, pageSize);
//        List list = jdbcTemplate.query(querySql, args, BeanPropertyRowMapper.newInstance(type));
//
//        String countSql = StringUtils.isEmpty(sumSql) ? String.format("select count(0) from (%s)t ", sql) : sumSql;
//        Long count = jdbcTemplate.queryForObject(countSql, args, Long.class);
//
//        return new PageModel(list, count, pageIndex, pageSize);
//    }


    @Override
    public <T> PageModel<T> pageQuery(Class<T> type, String sql, String sumSql, int pageIndex, int pageSize, @Nullable Object... args) {

        SqlWoker sqlWoker = SqlWorkerFactory.create(jdbcTemplate.getDataSource());
        String querySql = sqlWoker.getPageSql(sql, pageIndex, pageSize);
        String countSql = sqlWoker.getCountSql(sql);

        List list = jdbcTemplate.query(querySql, args, BeanPropertyRowMapper.newInstance(type));

        String countSqlStr = StringUtils.isEmpty(sumSql) ? countSql : sumSql;
        Long count = jdbcTemplate.queryForObject(countSqlStr, args, Long.class);

        return new PageModel(list, count, pageIndex, pageSize);
    }

    @Override
    public PageModel<Map<String, Object>> pageQueryForMap(String sql, int pageIndex, int pageSize, @Nullable Object... args) {
        return this.pageQueryForMap(sql, "", pageIndex, pageSize, args);
    }

    @Override
    public PageModel<Map<String, Object>> pageQueryForMap(String sql, String sumSql, int pageIndex, int pageSize, @Nullable Object... args) {

        SqlWoker sqlWoker = SqlWorkerFactory.create(jdbcTemplate.getDataSource());
        String querySql = sqlWoker.getPageSql(sql, pageIndex, pageSize);
        String countSql = sqlWoker.getCountSql(sql);

        List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql, args);

        String countSqlStr = StringUtils.isEmpty(sumSql) ? countSql : sumSql;
        Long count = jdbcTemplate.queryForObject(countSqlStr, args, Long.class);

        return new PageModel(list, count, pageIndex, pageSize);
    }


    @Override
    public <T> PageModel<T> pageQuery(Class<T> type, String sql, int pageIndex, int pageSize, @Nullable Object... args) {
        return this.pageQuery(type, sql, null, pageIndex, pageSize, args);
    }

    @Override
    public long count(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, args, Long.class);
    }

    @Override
    public long add(String sql, Object... args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i] == null ? "" : args[i]);
                }
            }
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public void execute(String sql, Object... args) {
        jdbcTemplate.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i] == null ? "" : args[i]);
                }
            }
            return ps;
        }, (PreparedStatementCallback<Object>) ps -> ps.execute());
    }

    @Override
    public int[][] batch(String sql, List<Object[]> batchArgs, int batchSize) {
        return jdbcTemplate.batchUpdate(sql, batchArgs, batchSize, (ps, args) -> {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i] == null ? "" : args[i]);
                    //StatementCreatorUtils.setParameterValue(ps, colIndex, colType, value);
                }
            }
        });
    }


}
