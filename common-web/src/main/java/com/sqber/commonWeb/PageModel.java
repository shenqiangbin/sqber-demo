package com.sqber.commonWeb;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageModel<T> {
    public List<T> list;
    public int pageIndex;
    public int pageSize;
    public long totalPage;
    public long totalCount;

    public PageModel() {
    }

    public PageModel(List<T> list, long totalCount, int pageIndex, int pageSize) {
        this.list = list;
        this.pageIndex = pageIndex;
        this.totalCount = totalCount;
        this.pageSize = pageSize;

        this.totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
    }

    public PageModel(PageInfo pageInfo, List list) {
        this.list = list;
        this.pageIndex = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalCount = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
