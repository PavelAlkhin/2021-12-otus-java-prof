import model.Atm;
import model.Banknote;
import model.Nominal;
import service.GetMoneyService;
import service.InitialMoneySeederService;

import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        var atm = new Atm();

        var initService = new InitialMoneySeederService(atm);
        initService.putBanknotes(); //88_800

        var getMoneyService = new GetMoneyService(atm, 80_005);

        Map<Nominal, List<Banknote>> moneyFromAtm = getMoneyService.getMoneyFromAtm();

        for (var entry : moneyFromAtm.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().size());
        }

    }

}
