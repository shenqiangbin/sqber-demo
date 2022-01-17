package com.sqber.dbdemo.model.request;

import com.sqber.dbdemo.model.Resource;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResourceSaveRequest extends ResourceAddRequest {

    @NotNull(message = "id不能为空")
    private Integer id;
}
