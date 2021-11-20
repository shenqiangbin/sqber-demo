package com.sqber.commonTool.db.sql.impl;

import com.sqber.commonTool.db.sql.SqlWoker;

public class SqlWorker2Postgre implements SqlWoker {

    @Override
    public String getPageSql(String sql, int pageIndex, int pageSize) {
        int startIndex = (pageIndex - 1) * pageSize;
        return String.format("%s limit %s offset %s", sql, pageSize, startIndex);
    }

    @Override
    public String getCountSql(String sql) {
        return String.format("select count(0) from (%s)t ", sql);
    }
}
