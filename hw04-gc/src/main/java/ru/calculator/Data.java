package ru.calculator;

public class Data {
//    private final Integer value;
    private int value;

    public Data(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Data() {
    }

    public int getValue() {
        return value;
    }
}
