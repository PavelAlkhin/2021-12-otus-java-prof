package model;

import java.util.*;

public class Atm {

    private final Map<Nominal, Cell> cellMap;

    public Atm() {
        this.cellMap = new HashMap<>();
        Arrays.asList(Nominal.values()).forEach(n -> this.cellMap.put(n, new Cell(n)));
    }
    
    public void addBanknotes(List<Banknote> banknotes){
        banknotes.forEach(b->cellMap.get(b.getNominal()).addBanknote(b));
    }

    public int getBalance(){
        final int[] total = {0};
        cellMap.forEach( (key, value) -> total[0] += value.getTotalAmount() );
        return total[0];
    }

    public Map<Nominal, List<Banknote>> getBanknotes(int amount){
        Map<Nominal, List<Banknote>> moneyMap = new HashMap<>();
        for (var nom : Nominal.values()){
            Cell cell = cellMap.get(nom);
            List<Banknote> money = new ArrayList<>();
            for(var b : cell.getBanknotes()){
                if (amount >= b.getAmountNominal()){
                    amount -= b.getAmountNominal();
                    money.add(b);
                }else{
                    break;
                }
            }
            moneyMap.put(nom, money);
        }
        return moneyMap;
    }
}
