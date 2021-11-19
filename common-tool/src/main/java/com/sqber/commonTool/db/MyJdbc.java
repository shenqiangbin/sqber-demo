package com.sqber.commonTool.db;

import com.sqber.commonTool.db.model.PageModel;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface MyJdbc {

    <T> List<T> query(Class<T> type, String sql, @Nullable Object... args);

    List<Map<String, Object>> queryForMap(String sql, @Nullable Object... args);

    <T> PageModel<T> pageQuery(Class<T> type, String sql, String sumSql, int pageIndex, int pageSize, @Nullable Object... args);
}
