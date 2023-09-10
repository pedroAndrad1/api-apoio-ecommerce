package br.com.uniriotec.sagui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {
    @Value("${mysql.on-premisses.url}")
    private String url;
    @Value("${mysql.on-premisses.user}")
    private String user;
    @Value("${mysql.on-premisses.password}")
    private String password;
    @Value("${mysql.on-premisses.dbname}")
    private String dbname;
    @Value("${mysql.on-premisses.driver}")
    private String driver;

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                //.type(HikariDataSource.class)
                .driverClassName(driver)
                .url( url + dbname )
                .username( user )
                .password( password )
                .build();
    }
    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
