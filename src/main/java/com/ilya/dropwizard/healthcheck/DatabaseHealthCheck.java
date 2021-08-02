package com.ilya.dropwizard.healthcheck;

import com.ilya.dropwizard.DAO.PersonDAO;
import com.codahale.metrics.health.HealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthCheck extends HealthCheck {

    @Autowired
    private  PersonDAO personDAO;

    @Override
    protected Result check() throws Exception {
        if(personDAO.getConnection()!=null){
            return Result.healthy();
        }
        else{
            return Result.unhealthy("Cannot connect to db");
        }
    }
}
