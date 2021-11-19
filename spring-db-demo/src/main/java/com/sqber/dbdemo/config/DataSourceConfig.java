package com.sqber.dbdemo.config;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.impl.MyJdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public MyJdbc primaryJdbc(DataSource dataSource) {
        return new MyJdbcTemplate(dataSource);
    }
}
