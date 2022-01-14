package com.sqber.dbdemo.model;

import com.sqber.commonTool.myenum.IEnum;
import com.sqber.commonWeb.entity.BaseDO;
import lombok.Data;

@Data
public class Resource extends BaseDO {
    private Integer id;
    private String name;
    private Integer category;
    private String requestUrl;
    private String apis;

    public enum Category implements IEnum {
        Nav("导航资源", 100),
        Button("按钮资源", 101);

        private final String name;

        private Integer value;

        private Category(String name, Integer value) {
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
}
