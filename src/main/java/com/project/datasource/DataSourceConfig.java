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

    @Bean
    @ConfigurationProperties("spring.datasource.hikari.allocation-data")
    public DataSource allocationDataSource(){
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }


    @Bean
    public PlatformTransactionManager allocationTransactionManager(){
        return new JdbcTransactionManager(allocationDataSource());
    }


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari.batch-status")
    public DataSource batchStatusDataSource(){
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }


    @Bean
    @Primary
    public PlatformTransactionManager batchStatusTransactionManager() {
        return new JdbcTransactionManager(batchStatusDataSource());
    }


}
