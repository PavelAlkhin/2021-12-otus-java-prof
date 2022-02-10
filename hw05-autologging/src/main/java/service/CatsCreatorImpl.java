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

    @Log
    @Override
    public void addForCats(){
        for (int i = 0; i<4; i++){
            cats.add(new Cat("Cat"+i));
        }
    }

    @Log
    @Override
    public void addCat(Cat cat){
        cats.add(cat);
    }

    @Log
    @Override
    public void removeCat(Cat cat){
        cats.remove(cat);
    }

    @Log
    @Override
    public List<Cat> getCats() {
        return cats;
    }

    @Log
    @Override
    public void clearCats(){
        cats.clear();
    }

    @Log
    @Override
    public int countCats(){
        return cats.size();
    }

}
