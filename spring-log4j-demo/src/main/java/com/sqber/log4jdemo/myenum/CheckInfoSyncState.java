package com.sqber.log4jdemo.myenum;

import com.sqber.commonTool.myenum.IEnum;

public enum CheckInfoSyncState implements IEnum {
    DEFAULT_VAL("默认值", 0),
    SUCCESS("成功", 20);

    private final String name;

    private Integer value;

    private CheckInfoSyncState(String name, Integer value) {
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
