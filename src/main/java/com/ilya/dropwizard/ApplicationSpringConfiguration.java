package com.ilya.dropwizard;

import com.ilya.dropwizard.DAO.DaoSpringConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DaoSpringConfiguration.class})
@ComponentScan(
        basePackages = {"com.ilya.dropwizard"},
        basePackageClasses = MainApplication.class)
public class ApplicationSpringConfiguration {


}
