package service;

import model.Atm;
import model.Banknote;
import model.Nominal;

import java.util.ArrayList;
import java.util.List;

public class InitialMoneySeederService {

    private final Atm atm;

    public InitialMoneySeederService(Atm atm) {
        this.atm = atm;
    }

    public void putBanknotes(){
        atm.addBanknotes(getBanknotes());
    }

    private List<Banknote> getBanknotes(){
        List<Banknote> list = new ArrayList<>();
        putBanknotesByNominal(list, Nominal.FIVE_HUNDRED, 100); //500 * 100 = 50_000
        putBanknotesByNominal(list, Nominal.TWO_HUNDRED, 100); //200 * 100 = 20_000
        putBanknotesByNominal(list, Nominal.HUNDRED, 100); // 100 * 100 = 10_000
        putBanknotesByNominal(list, Nominal.FIFTY, 100); // 50 * 100 = 5_000
        putBanknotesByNominal(list, Nominal.TWENTY, 100); // 20 * 100 = 2_000
        putBanknotesByNominal(list, Nominal.TEN, 100); //10 * 100 = 1_000  / 88_000
        putBanknotesByNominal(list, Nominal.FIVE, 100); // = 500
        putBanknotesByNominal(list, Nominal.TWO, 100); // = 200
        putBanknotesByNominal(list, Nominal.ONE, 100); // = 100 // = 88_800
        return list;
    }

    private void putBanknotesByNominal(List<Banknote> list, Nominal nom, int count) {
        for(int i = 0; i < count; i++) {
            list.add(new Banknote(nom));
        };
    }
}
