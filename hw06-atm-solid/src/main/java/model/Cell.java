package model;

import exception.NominalAmountException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cell {
    private List<Banknote> banknotes;
    private final Nominal nominal;
    private int totalAmount;
    private int count;

    public Cell(Nominal nominal) {
        this.banknotes = new ArrayList<>();
        this.nominal = nominal;
    }

    public boolean addBanknote(Banknote banknote){
        if (nominal != banknote.getNominal()){
            throw new NominalAmountException("Error. Cell is not for this Banknote.");
        }
        banknotes.add(banknote);
        totalAmount = banknotes.size() * nominal.getAmount();
        count += 1;
        return true;
    }

    public List<Banknote> getBanknotes() {
        return Collections.unmodifiableList(banknotes);
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getCount() {
        return count;
    }

    public int getAmountNominal() {
        return nominal.getAmount();
    }

    public Nominal getNominal() {
        return nominal;
    }
}
