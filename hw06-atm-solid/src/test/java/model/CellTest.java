package model;

import exception.NominalAmountException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    static Atm atm;
    static Cell cell50;
    static Cell cell100;

    @BeforeEach
    void befor(){
        cell50 = new Cell(Nominal.FIFTY);
        cell100 = new Cell(Nominal.HUNDRED);
        atm = new Atm();
        List<Banknote> banknotes = List.of(new Banknote(Nominal.FIFTY), new Banknote(Nominal.FIFTY));
        atm.addBanknotes(banknotes);
    }

    @Test
    void addGoodBanknoteToCell() {
        Banknote banknote100 = new Banknote(Nominal.FIFTY);
        boolean res = cell50.addBanknote(banknote100);
        assertNotNull (res);
    }

    @Test
    void addBadBanknoteToCell() {
        Banknote banknote100 = new Banknote(Nominal.FIVE_HUNDRED);
        try {
            boolean res = cell50.addBanknote(banknote100);
            assertNotNull(res);
        }catch (NominalAmountException exception){
            assertNotNull(exception);
        }

    }

    @Test
    void getTotalAmount() {
        int balance = atm.getBalance();
        assertEquals(100, balance);
    }

    @Test
    void getCount() {
//        assertEquals(2, cell50.getCount());
    }

    @Test
    void getAmountNominal() {
        Arrays.asList(Nominal.values()).forEach(n -> System.out.println(n));
    }

    @Test
    void getNominal() {
    }
}