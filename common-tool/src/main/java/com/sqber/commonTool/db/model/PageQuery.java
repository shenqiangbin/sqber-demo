package com.sqber.commonTool.db.model;

public class PageQuery {

    private int currentPage = 1;
    private int pageSize = 10;
    private String sortStr;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
