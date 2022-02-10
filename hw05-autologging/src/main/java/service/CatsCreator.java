package service;

import entries.Cat;

import java.util.List;

public interface CatsCreator {

    List<Cat> newCatsList();

    void addForCats();

    void addCat(Cat cat);

    void removeCat(Cat cat);

    List<Cat> getCats();

    void clearCats();

    int countCats();
}
