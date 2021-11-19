package com.sqber.dbdemo.controller;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.model.PageModel;
import com.sqber.commonWeb.R;
import com.sqber.dbdemo.myenum.RecordStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqber.dbdemo.model.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("jdbc")
public class JdbcController {

    private JdbcTemplate jdbcTemplate;
    private MyJdbc myJdbc;

    public JdbcController(JdbcTemplate jdbcTemplate, MyJdbc myJdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.myJdbc = myJdbc;
    }

    // query：查询所有（比如用在下拉列表）
    @GetMapping("/t1")
    public R t1() {
        String sql = "select * from user where status = ?";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        List<User> users = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(User.class));
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
        return R.success(users);
    }

    // 这个抽离一个
    //public <T> List<T> query(String sql, Object[] args, Class<T> type);

    @GetMapping("/t1my")
    public R t1my() {
        String sql = "select * from user where status = ?";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        List<User> users = myJdbc.query(User.class, sql, args);
        List<Map<String, Object>> map = myJdbc.queryForMap(sql, args);

        return R.success(users);
    }


    // query：分页查询（我们可以对查询进一步封装）
    @GetMapping("/pageQuery")
    public R testPageQuery() {
        String sql = "select * from user where status = ? ";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        PageModel<User> pageModel = pageQuery(sql, null, args, 1, 2, User.class);
        return R.success(pageModel);
    }

    private <T> PageModel<T> pageQuery(String sql, String sumSql, Object[] args, int pageIndex, int pageSize, Class<T> type) {
        int startIndex = (pageIndex - 1) * pageSize;
        String querySql = String.format("%s limit %s,%s", sql, startIndex, pageSize);
        List<User> list = jdbcTemplate.query(querySql, args, BeanPropertyRowMapper.newInstance(User.class));

        String countSql = StringUtils.isEmpty(sumSql) ? String.format("select count(0) from (%s)t ", sql) : sumSql;
        Long count = jdbcTemplate.queryForObject(countSql, args, Long.class);

        return new PageModel(list, count, pageIndex, pageSize);
    }

    // query：根据 id 查询
    @GetMapping("getById")
    public R getById(int id) {
        String sql = "select * from user where status = ? and id = ?";
        Object[] args = new Object[]{RecordStatus.EXISTS.getVal(), id};
        List<User> users = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(User.class));
        return R.success(users);
    }
    // query：根据某列查询（根据 userCode 查询）(和上面的根据 id 查询类似)

    // query：查询总数
    @GetMapping("count")
    public R count() {
        String sql = "select count(*) from user where status = ?";
        Object[] args = {RecordStatus.EXISTS.getVal()};
        Long count = this.jdbcTemplate.queryForObject(sql, args, Long.class);
        return R.success(count);
    }

    // add: 新增
    @GetMapping("add")
    public R add() {
        String INSERT_SQL = "insert into user (userCode,userName) values(?,?)";
        Object[] args = {"Rob", "肉搏"};

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i] == null ? "" : args[i]);
                }
            }
            return ps;
        }, keyHolder);
        long rowId = keyHolder.getKey().longValue();

        return R.success(rowId);
    }

    // update：更新
    @GetMapping("save")
    public R save() {
        String sql = "update user set username = ? where id = ?";
        Object[] args = {"管理员", 1};
        this.jdbcTemplate.update(sql, args);
        return R.success();
    }


    // execute：执行
    @GetMapping("exe")
    public R exe() {
        String sql = "CREATE TABLE mytest (\n" +
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

        this.jdbcTemplate.execute(sql);
//        this.jdbcTemplate.execute(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            if (args != null) {
//                for (int i = 0; i < args.length; i++) {
//                    ps.setObject(i + 1, args[i] == null ? "" : args[i]);
//                }
//            }
//            return ps;
//        }, (PreparedStatementCallback<Object>) ps -> ps.execute());
        return R.success();
    }

    //execute： 批量执行
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

        executeBatch(sql, list);

        return R.success();
    }

    private int[] executeBatch(String sql, List<Object[]> batchArgs) {
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }


}
