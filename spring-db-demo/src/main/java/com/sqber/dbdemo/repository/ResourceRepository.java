package com.sqber.dbdemo.repository;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.model.PageModel;
import com.sqber.dbdemo.model.Resource;
import com.sqber.dbdemo.model.request.ResourcePageRequest;
import com.sqber.dbdemo.myenum.RecordStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResourceRepository {

    private MyJdbc myJdbc;

    public ResourceRepository(MyJdbc myJdbc) {
        this.myJdbc = myJdbc;
    }

    public PageModel<Resource> getPageList(ResourcePageRequest request) {
        String sql = "select * from resource";
        List<Object> argsList = new ArrayList<>();
        List<String> where = new ArrayList<>();

        where.add("status = ?");
        argsList.add(RecordStatus.EXISTS.getVal());

        if (StringUtils.isNotBlank(request.getName())) {
            where.add(" name like ?");
            argsList.add("%" + request.getName() + "%");
        }
        if (StringUtils.isNotBlank(request.getCategory())) {
            where.add(" category = ? ");
            argsList.add(request.getCategory());
        }
        if (where.size() > 0) {
            sql += " where " + String.join(" and ", where);
        }

        String sortStr = "id desc";
        if (StringUtils.isNotBlank(request.getSortStr())) {
            sortStr = request.getSortStr();
        }
        sql += " order by " + sortStr;

        PageModel<Resource> pageModel = myJdbc.pageQuery(Resource.class, sql, request.getPageIndex(), request.getPageSize(), argsList.toArray());
        return pageModel;
    }

}
