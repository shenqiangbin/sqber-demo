package com.sqber.dbdemo.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResourceAddRequest {

    @NotBlank(message = "名称不能为空")
    @Size(min = 0, max = 150, message = "最大长度不超过150")
    private String name;

    //@NotBlank(message = "路由不能为空")
    @Size(min = 0, max = 150, message = "最大长度不超过150")
    private String requestUrl;

    @NotNull(message = "分类不能为空")
    private Integer category;
}
