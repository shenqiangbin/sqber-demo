package com.sqber.dbdemo.config;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.impl.MyJdbcTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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

    @Bean("dataMyJdbc")
    public MyJdbc dataMyJdbc(@Qualifier("dataDataSource") DataSource dataSource) {
        return new MyJdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.userdb") // application.properteis中对应属性的前缀
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("dataDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.datadb")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }


}
