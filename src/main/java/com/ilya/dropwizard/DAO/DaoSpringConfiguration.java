package com.ilya.dropwizard.DAO;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:application.properties")
public class DaoSpringConfiguration {

//    @Value("${db_url}") String url,
//    @Value("${db_user}") String user,
//    @Value("${db_password}") String password

    @Bean
    public BasicDataSource basicDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/test_db");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("123");
        basicDataSource.setDriverClassName("org.postgresql.Driver");

        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(basicDataSource());
    }
}
