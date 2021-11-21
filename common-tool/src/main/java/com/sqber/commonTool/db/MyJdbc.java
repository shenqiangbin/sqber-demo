package com.sqber.commonTool.db;

import com.sqber.commonTool.db.model.PageModel;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface MyJdbc {

//    public static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
//    public static final String DRIVER_MYSQL8 = "com.mysql.cj.jdbc.Driver";
    public static final String DRIVER_MYSQL = "MySQL Connector/J";
    public static final String DRIVER_POSTGRE = "PostgreSQL JDBC Driver";

    <T> List<T> query(Class<T> type, String sql, @Nullable Object... args);

    List<Map<String, Object>> queryForMap(String sql, @Nullable Object... args);

    <T> PageModel<T> pageQuery(Class<T> type, String sql, int pageIndex, int pageSize, @Nullable Object... args);

    <T> PageModel<T> pageQuery(Class<T> type, String sql, String sumSql, int pageIndex, int pageSize, @Nullable Object... args);

    PageModel<Map<String, Object>> pageQueryForMap(String sql, int pageIndex, int pageSize, @Nullable Object... args);

    PageModel<Map<String, Object>> pageQueryForMap(String sql, String sumSql, int pageIndex, int pageSize, @Nullable Object... args);

    long count(String sql, @Nullable Object... args);

    long add(String sql, @Nullable Object... args);

    int update(String sql, @Nullable Object... args);

    void execute(String sql, @Nullable Object... args);

    int[][] batch(String sql, List<Object[]> batchArgs, int batchSize);
}
