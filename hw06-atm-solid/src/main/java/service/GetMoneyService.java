package service;

import model.Atm;
import model.Banknote;
import model.Nominal;

import java.util.List;
import java.util.Map;

public class GetMoneyService {

    private final Atm atm;
    private int amountToGet;
    private int remainingMoney;

    public GetMoneyService(Atm atm, int amountToGet) {
        this.atm = atm;
        this.amountToGet = amountToGet;
    }

    public void setAmountToGet(int amountToGet) {
        this.amountToGet = amountToGet;
    }

    public int getRemainingMoney() {
        return remainingMoney;
    }

    public Map<Nominal, List<Banknote>> getMoneyFromAtm(){
        int ostatok = amountToGet;
        Map<Nominal, List<Banknote>> banknotes = atm.getBanknotes(ostatok);
        remainingMoney = amountToGet - ostatok;
        return banknotes;
    }
}
