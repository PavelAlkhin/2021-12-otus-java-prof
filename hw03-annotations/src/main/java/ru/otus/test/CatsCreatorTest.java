package ru.otus.test;

import ru.otus.annotation.After;
import ru.otus.annotation.Befor;
import ru.otus.annotation.Test;
import ru.otus.entries.Cat;
import ru.otus.service.CatsCreator;

public class CatsCreatorTest {

    private CatsCreator catsCreator;

    @Befor
    public void befor(){
        catsCreator = new CatsCreator();
        catsCreator.addForCats();
    }

    @Test
    public void createMore4Cats(){
        if (catsCreator.countCats() != 4) {
            throw new RuntimeException("Cats must be 4");
        }
        catsCreator.addCat(new Cat("Mimimi"));
        if (catsCreator.countCats() != 5) {
            throw new RuntimeException("Cats must be 5");
        }
    }

    @Test
    public void compareCatsWithRemoved(){
        Cat catOne = catsCreator.getCats().get(0);
        catsCreator.removeCat(catOne);
        Cat catOneAgain = catsCreator.getCats().get(0);
        if (catOne.getName() == catOneAgain.getName()){
            throw new RuntimeException("Cat did not remove");
        }
    }

    @After
    public void after(){
        catsCreator.clearCats();
    }
}
