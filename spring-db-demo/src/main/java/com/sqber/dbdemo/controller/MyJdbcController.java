package com.sqber.dbdemo.controller;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.model.PageModel;
import com.sqber.commonWeb.R;
import com.sqber.dbdemo.model.Person;
import com.sqber.dbdemo.model.User;
import com.sqber.dbdemo.myenum.RecordStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/myJdbc")
public class MyJdbcController {

    private MyJdbc myJdbc;

    private MyJdbc dataMyJdbc;

    private TransactionTemplate transactionTemplate;

    public MyJdbcController(
            MyJdbc myJdbc,
            @Qualifier("dataMyJdbc") MyJdbc dataMyJdbc,
            TransactionTemplate transactionTemplate) {
        this.myJdbc = myJdbc;
        this.dataMyJdbc = dataMyJdbc;
        this.transactionTemplate = transactionTemplate;
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

    @GetMapping("/pageQueryMap")
    public R pageQueryMap() {
        String sql = "select * from user where status = ? ";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        PageModel pageModel = myJdbc.pageQueryForMap(sql, 1, 2, args);
        return R.success(pageModel);
    }

    // test postgresql
    @GetMapping("/pageQuery2")
    public R testPageQuery2() {
        String sql = "select * from person";
        PageModel pageModel = dataMyJdbc.pageQuery(Person.class, sql, 1, 2);
        return R.success(pageModel);
    }

    @GetMapping("getById")
    public R getById(int id) {
        String sql = "select * from user where status = ? and id = ?";
        Object[] args = new Object[]{RecordStatus.EXISTS.getVal(), id};
        List<User> users = myJdbc.query(User.class, sql, args);
        return R.success(users);
    }

    @GetMapping("count")
    public R count() {
        String sql = "select count(*) from user where status = ?";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        Long count = myJdbc.count(sql, args);
        return R.success(count);
    }

    @GetMapping("add")
    public R addmy() {
        String sql = "insert into user (userCode,userName) values(?,?)";
        Object[] args = {"Rob", "肉搏"};
        long id = myJdbc.add(sql, args);
        return R.success(id);
    }

    @GetMapping("save")
    public R save() {
        String sql = "update user set username = ? where id = ?";
        Object[] args = {"管理员123", 1};
        int affectedNum = myJdbc.update(sql, args);
        return R.success(affectedNum);
    }

    @GetMapping("exe")
    public R exe() {
        String sql = "CREATE TABLE mytest1(\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `userCode` varchar(64) DEFAULT '' COMMENT '账号',\n" +
                "  `userName` varchar(64) DEFAULT '' COMMENT '显示名称',\n" +
                "  `password` varchar(64) DEFAULT '' COMMENT '密码',\n" +
                "  `status` int(11) DEFAULT '1' COMMENT '0-记录已删除；1-未删除',\n" +
                "  `createUser` varchar(64) DEFAULT NULL,\n" +
                "  `createTime` datetime DEFAULT NULL,\n" +
                "  `modifyUser` varchar(64) DEFAULT NULL,\n" +
                "  `modifyTime` datetime DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB;";
        Object[] args = {"mytest"};

        myJdbc.execute(sql);
        return R.success();
    }

    @GetMapping("batchExe")
    public R batchExe() {
        String sql = "insert into user (userCode,userName) values(?,?)";

        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"tom1", "tom1"});
        list.add(new Object[]{"tom2", "tom2"});
        list.add(new Object[]{"tom3", "tom3"});
        list.add(new Object[]{"tom4", "tom4"});
        list.add(new Object[]{"tom5", "tom5"});
        list.add(new Object[]{"tom6", "tom6"});
        list.add(new Object[]{"tom7", "tom7"});

        int[][] result = myJdbc.batch(sql, list, 2);
        Long count = MyJdbc.count(result);
        return R.success(count);
    }

    @GetMapping("addGroup")
    public R addGroup() {
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                String sql = "insert into user (userCode,userName) values(?,?)";
                Object[] args = {"Rob", "肉搏"};
                long id = myJdbc.add(sql, args);

                String sql2 = "insert into user (userCode1,userName) values(?,?)";
                Object[] args2 = {"Rob", "肉搏"};
                id = myJdbc.add(sql2, args2);

//                status.setRollbackOnly();
                return null;
            }
        });
        return R.success();
    }
}
