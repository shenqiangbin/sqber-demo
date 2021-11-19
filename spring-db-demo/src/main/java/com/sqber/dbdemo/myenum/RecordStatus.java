package com.sqber.dbdemo.myenum;

import com.sqber.commonTool.myenum.IEnum;

public enum RecordStatus implements IEnum {
    EXISTS("未删除", 1),
    DELETED("删除", 0);

    private final String name;

    private Integer value;

    private RecordStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getVal() {
        return this.value;
    }
}
