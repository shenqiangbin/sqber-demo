package com.sqber.commonTool.db.model;

public class PageQuery {

    private int pageIndex = 1;
    private int pageSize = 10;
    private String sortStr;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getSortStr() {
        if (sortStr != null) {
            if (sortStr.contains("update") || sortStr.contains("delete") || sortStr.contains("--")) {
                return "";
            }
        }
        return sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
