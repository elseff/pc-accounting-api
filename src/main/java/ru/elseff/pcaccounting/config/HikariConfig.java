package ru.elseff.pcaccounting.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HikariConfig {
    @Bean
    public HikariPoolMXBean test(DataSource dataSource){
        HikariDataSource hikariDs = (HikariDataSource) dataSource;
        HikariPoolMXBean poolBean = hikariDs.getHikariPoolMXBean();
        poolBean.softEvictConnections();

        return poolBean;
    }
}
