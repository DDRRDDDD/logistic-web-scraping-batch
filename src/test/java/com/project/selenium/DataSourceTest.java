package com.project.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DataSourceTest {

    private final Logger log = LoggerFactory.getLogger(DataSourceTest.class);
    private final DataSource dataSource;


    @Autowired
    public DataSourceTest(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Test
    @DisplayName("DataSource 구현체 확인 테스트")
    public void dataSourceImplementsClassTest(){
        String className = dataSource.getClass().getSimpleName();

        log.info("DataSource class Name >>> {}", className);
        Assertions.assertEquals("HikariDataSource", className);
    }

    @Test
    @DisplayName("DataSource의 메타 데이터를 잘 불러오는지 테스트")
    public void dataSourceMetaDataTest() throws SQLException {
        Connection connection = dataSource.getConnection();

        String driverName = connection.getMetaData().getDriverName();
        String jdbcUrl = connection.getMetaData().getURL();
        String userName = connection.getMetaData().getUserName();

        log.info("DataSource MetaData => Driver: {}, URL: {}, User: {}", driverName, jdbcUrl, userName);

        Assertions.assertEquals("H2 JDBC Driver", driverName);
        Assertions.assertEquals("SA", userName);
    }

    @Test
    @DisplayName("HikariCP network timeout 테스트")
    public void test() throws SQLException{
        int timeout = dataSource.getConnection().getNetworkTimeout();

        log.info("HikariCP timeout seconds => {}", timeout);

    }
}
