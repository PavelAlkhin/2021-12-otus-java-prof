package ru.otus.service;

import ru.otus.entries.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatsCreator {

    List<Cat> cats;

    public CatsCreator() {
        this.cats = new ArrayList<>();
    }

    public List<Cat> newCatsList(){
        List<Cat> c = new ArrayList<>();
        return cats;
    }

    public void addForCats(){
        for (int i = 0; i<4; i++){
            cats.add(new Cat("Cat"+i));
        }
    }

    public void addCat(Cat cat){
        cats.add(cat);
    }

    public void removeCat(Cat cat){
        cats.remove(cat);
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void clearCats(){
        cats.clear();
    }

    public int countCats(){
        return cats.size();
    }

}
