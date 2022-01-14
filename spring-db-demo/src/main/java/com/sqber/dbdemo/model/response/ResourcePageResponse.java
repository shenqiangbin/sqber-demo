package com.sqber.dbdemo.model.response;

import com.sqber.commonTool.myenum.IEnum;
import com.sqber.dbdemo.model.Resource;
import lombok.Data;

import java.util.List;

@Data
public class ResourcePageResponse {
    private String name;
    private Integer category;
    private String categoryStr;
    private String requestUrl;
    private String apis;

    public static void setCategoryStrVal(List<ResourcePageResponse> list) {
        if (list == null || list.size() == 0)
            return;

        for (ResourcePageResponse item : list) {
            String str = IEnum.geNameByVal(Resource.Category.class, item.getCategory());
            item.setCategoryStr(str);
        }
    }
}
