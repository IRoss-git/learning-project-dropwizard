package com.ilya.dropwizard;

import com.ilya.dropwizard.DAO.DaoSpringConfiguration;
import com.ilya.dropwizard.service.PersonService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Import(DaoSpringConfiguration.class)
@ComponentScan(
        basePackages = {"com.ilya.dropwizard"},
        basePackageClasses = MainApplication.class)
public class ApplicationSpringConfiguration {


}
