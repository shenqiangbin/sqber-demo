package com.sqber.dbdemo.controller;

import com.sqber.commonTool.ListUtil;
import com.sqber.commonTool.excel.CommonExcel;
import com.sqber.commonTool.excel.SaveResult;
import com.sqber.commonTool.myenum.IEnum;
import com.sqber.commonWeb.R;
import com.sqber.dbdemo.model.SaleModel;
import com.sqber.dbdemo.myenum.CheckInfoSyncState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("db")
public class DbController {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public DbController(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/t1")
    public R t1() {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user");
        return R.success(maps);
    }

    @GetMapping("/t2")
    public R t2() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("update user set username = '管理页1' where id = 1");
        }
        return R.success();
    }

}
