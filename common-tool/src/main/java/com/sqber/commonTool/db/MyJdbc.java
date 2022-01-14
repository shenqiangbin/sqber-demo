package com.sqber.commonTool.db;

import com.sqber.commonTool.db.model.PageModel;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public interface MyJdbc {

    String DRIVER_MYSQL = "MySQL Connector/J";
    String DRIVER_POSTGRE = "PostgreSQL JDBC Driver";

    static Long count(int[][] batch) {
        AtomicLong count = new AtomicLong();
        Arrays.stream(batch).forEach(item ->
                Arrays.stream(item).forEach(innerItem ->
                        count.set(innerItem + count.get())
                )
        );
        return count.get();
    }

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
