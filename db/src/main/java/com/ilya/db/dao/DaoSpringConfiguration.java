package com.ilya.db.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:db.properties")
public class DaoSpringConfiguration {

    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    @Bean
    public BasicDataSource basicDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName("org.postgresql.Driver");

        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(basicDataSource());
    }
}
