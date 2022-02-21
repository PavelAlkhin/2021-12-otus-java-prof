import entries.Cat;
import proxy.ProxyLogging;
import service.CatsCreator;

public class Main {
    public static void  main(String[] args){

        CatsCreator catsCreator = ProxyLogging.createMyClass();
        Cat catOne = new Cat("Murch");

//        catsCreator.addForCats();
//        Cat catOne = catsCreator.getCats().get(0);
//        catsCreator.removeCat(catOne);
//        Cat catOneAgain = catsCreator.getCats().get(0);
//        if (catOne.getName() == catOneAgain.getName()){
//            throw new RuntimeException("Cat did not remove");
//        }

        catsCreator.addCats(catOne, catOne, catOne);
        catsCreator.addCats(catOne, catOne);

        for (Cat с : catsCreator.getCats()) {
            System.out.println(с);
        }

    }
}
