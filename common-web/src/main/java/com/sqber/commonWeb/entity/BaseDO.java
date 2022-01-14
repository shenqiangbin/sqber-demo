package com.sqber.commonWeb.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {
    private Integer status;
    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;

    public BaseDO() {

    }
}
