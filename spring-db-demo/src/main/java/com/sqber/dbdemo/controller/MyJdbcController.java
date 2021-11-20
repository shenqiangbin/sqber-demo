package com.sqber.dbdemo.controller;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonWeb.R;
import com.sqber.dbdemo.model.User;
import com.sqber.dbdemo.myenum.RecordStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myJdbc")
public class MyJdbcController {

    private MyJdbc myJdbc;

    public MyJdbcController(MyJdbc myJdbc) {
        this.myJdbc = myJdbc;
    }

    @GetMapping("/get")
    public R get(){
        String sql = "select * from user where status = ?";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        List<User> lists = myJdbc.query(User.class, sql, args);
        return R.success(lists);
    }
    

//    @GetMapping("/getAll")
//    public R getAll(){
//        String sql = "";
//        Object[] args = { "abc", "dee", }
//    }
}
