package ru.otus.entries;

public class Cat extends Animal {

    private String name;
    private String speech;

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getName() {
        return name;
    }

    public String getSpeech() {
        return speech;
    }

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", speech='" + speech + '\'' +
                '}';
    }
}
