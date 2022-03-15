package ru.otus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyMeasurement {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private double value;

    public MyMeasurement(String name, double value, String name1, double value1) {
        this.name = name1;
        this.value = value1;
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
