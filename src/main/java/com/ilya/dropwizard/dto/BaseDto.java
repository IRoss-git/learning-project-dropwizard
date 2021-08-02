package com.ilya.dropwizard.dto;

public abstract class BaseDto {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
