package com.sqber.dbdemo.controller;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.model.PageModel;
import com.sqber.commonWeb.R;
import com.sqber.dbdemo.model.Person;
import com.sqber.dbdemo.model.User;
import com.sqber.dbdemo.myenum.RecordStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myJdbc")
public class MyJdbcController {

    private MyJdbc myJdbc;

    private MyJdbc dataMyJdbc;

    public MyJdbcController(MyJdbc myJdbc, @Qualifier("dataMyJdbc") MyJdbc dataMyJdbc) {
        this.myJdbc = myJdbc;
        this.dataMyJdbc = dataMyJdbc;
    }

    @GetMapping("/queryAll")
    public R queryAll() {
        String sql = "select * from user where status = ?";
        Object[] args = {RecordStatus.EXISTS.getVal()};
//        List<User> users = myJdbc.query(User.class, sql, args);
        List<Map<String, Object>> users = myJdbc.queryForMap(sql, args);
        return R.success(users);
    }

    @GetMapping("/pageQuery")
    public R testPageQuery() {
        String sql = "select * from user where status = ? ";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        PageModel<User> pageModel = myJdbc.pageQuery(User.class, sql, 1, 2, args);
        return R.success(pageModel);
    }

    @GetMapping("/pageQuery2")
    public R testPageQuery2() {
        String sql = "select * from person";
        PageModel pageModel = dataMyJdbc.pageQuery(Person.class, sql, 1, 2);
        return R.success(pageModel);
    }
}
