package com.sqber.commonTool.db.sql;

import com.sqber.commonTool.db.MyJdbc;
import com.sqber.commonTool.db.sql.impl.SqlWorker2MySql;
import com.sqber.commonTool.db.sql.impl.SqlWorker2Postgre;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class SqlWorkerFactory {

    public static SqlWoker create(DataSource dataSource) {
        String driverClassName = null;
        try {
            driverClassName = getDirverClassName(dataSource);
        } catch (SQLException throwables) {
            throw new RuntimeException("get driver class faild:" + throwables.getMessage());
        }
        switch (driverClassName) {
            case MyJdbc.DRIVER_MSSQL:
            case MyJdbc.DRIVER_MSSQL8:
                return new SqlWorker2MySql();
            case MyJdbc.DRIVER_POSTGRE:
                return new SqlWorker2Postgre();
        }
        throw new RuntimeException("not known driver:" + driverClassName);
    }

    private static String getDirverClassName(DataSource dataSource) throws SQLException {
        DatabaseMetaData dbmd = dataSource.getConnection().getMetaData();
        return dbmd.getDriverName();
    }
}
