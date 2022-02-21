package model;

public enum Nominal {
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    HUNDRED(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10),
    FIVE(5),
    TWO(2),
    ONE(1);
    private int amount;

    Nominal(int i) {
        this.amount = i;
    }

    public int getAmount() {
        return amount;
    }
}
