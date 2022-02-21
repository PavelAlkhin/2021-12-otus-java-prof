package service;

import entries.Cat;

import java.util.List;

public interface CatsCreator {

    List<Cat> newCatsList();

    void addForCats();

    void addCat(Cat cat);

    void addCats(Cat cat1, Cat cat2);

    void addCats(Cat cat1, Cat cat2, Cat cat3);

    void removeCat(Cat cat);

    List<Cat> getCats();

    void clearCats();

    int countCats();
}
