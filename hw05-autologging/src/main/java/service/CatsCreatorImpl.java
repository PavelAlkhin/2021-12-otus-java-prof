package service;


import annotation.Log;
import entries.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatsCreatorImpl implements CatsCreator {

    List<Cat> cats;

    public CatsCreatorImpl() {
        this.cats = new ArrayList<>();
    }

    @Override
    public List<Cat> newCatsList(){
        List<Cat> c = new ArrayList<>();
        return cats;
    }

    @Override
    public void addForCats(){
        for (int i = 0; i<4; i++){
            cats.add(new Cat("Cat"+i));
        }
    }

    @Override
    public void addCat(Cat cat){
        cats.add(cat);
    }

    @Override
    @Log
    public void addCats(Cat cat1, Cat cat2) {
        this.addCat(cat1);
        this.addCat(cat2);
    }

    public void addCats(Integer cat1, Cat cat2) {
        this.addCat(cat2);
    }

    @Override
    @Log
    public void addCats(Cat cat1, Cat cat2, Cat cat3) {
        this.addCat(cat1);
        this.addCat(cat2);
        this.addCat(cat3);
    }

    @Override
    public void removeCat(Cat cat){
        cats.remove(cat);
    }

    @Override
    public List<Cat> getCats() {
        return cats;
    }

    @Override
    public void clearCats(){
        cats.clear();
    }

    @Override
    public int countCats(){
        return cats.size();
    }

}
