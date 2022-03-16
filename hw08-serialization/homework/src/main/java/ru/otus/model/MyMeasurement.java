package ru.otus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyMeasurement {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private double value;

    public MyMeasurement(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public MyMeasurement() {
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
