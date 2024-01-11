package com.project.datasource;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /**
     * 배치 실행 데이터 소스 빈 클래스들을 "dataSource"와 "transactionManager"로 찾고 있음
     * 참고 : DefaultBatchConfiguration getDataSource(), getTransactionManager()
     */

    public static final String BATCH_EXECUTION_DATA_SOURCE = "dataSource";
    public static final String BATCH_EXECUTION_TRANSACTION_MANAGER = "transactionManager";


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari.primary")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }


    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(){
        return new JdbcTransactionManager(primaryDataSource());
    }


    @Bean(BATCH_EXECUTION_DATA_SOURCE)
    @ConfigurationProperties("spring.datasource.hikari.batch-status")
    public DataSource batchStatusDataSource(){
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }


    @Bean(BATCH_EXECUTION_TRANSACTION_MANAGER)
    public PlatformTransactionManager batchStatusTransactionManager() {
        return new JdbcTransactionManager(batchStatusDataSource());
    }


}
