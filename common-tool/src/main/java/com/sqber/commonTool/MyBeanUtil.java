package com.sqber.commonTool;

import com.sqber.commonTool.db.model.PageModel;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MyBeanUtil {

    public static <T> List<T> trans(List list, Class<T> clazz) {
        ArrayList result = new ArrayList();
        for (Object item : list) {
            T t = BeanUtils.instantiateClass(clazz);
            trans(item, t);
            result.add(t);
        }
        return result;
    }

    public static void trans(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static <T> PageModel<T> transPage(PageModel info, Class<T> clazz) {
        PageModel<T> pageInfo = new PageModel();
        trans(info, pageInfo);
        List<T> trans = trans(info.getList(), clazz);
        pageInfo.setList(trans);
        return pageInfo;
    }
}
