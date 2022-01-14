package com.sqber.dbdemo.model.request;

import com.sqber.commonTool.db.model.PageQuery;
import lombok.Data;

@Data
public class ResourcePageRequest extends PageQuery {
    private String name;
    private String category;
}