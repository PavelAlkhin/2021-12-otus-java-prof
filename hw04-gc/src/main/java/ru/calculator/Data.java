package ru.calculator;

public class Data {
//    private final Integer value;
    private Integer value;

    public Data(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Data() {
    }

    public Integer getValue() {
        return value;
    }
}
