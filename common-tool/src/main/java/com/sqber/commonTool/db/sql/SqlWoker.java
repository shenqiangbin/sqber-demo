package com.sqber.commonTool.db.sql;

public interface SqlWoker {
    String getPageSql(String sql, int pageIndex, int pageSize);

    String getCountSql(String sql);
}
