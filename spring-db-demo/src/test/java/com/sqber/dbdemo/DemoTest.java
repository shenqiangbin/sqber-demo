package com.sqber.dbdemo;

import com.sqber.commonTool.db.MyJdbc;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.print.DocFlavor;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest
@EnableTransactionManagement
class DemoTests {

    private MyJdbc myJdbc;

    public DemoTests(MyJdbc myJdbc) {
        this.myJdbc = myJdbc;
    }

    @Test
    void contextLoads() throws Exception {

    }

}
