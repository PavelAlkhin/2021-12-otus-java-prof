package homework;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    NavigableMap<Customer, String> customers = new TreeMap<Customer, String>((o1, o2) -> Long.compare(o1.getScores(), o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> mapEntry = customers.firstEntry();
        Customer cst = (Customer) mapEntry.getKey();

        Map.Entry<Customer, String> entry =
                new AbstractMap.SimpleEntry<>(new Customer(
                        cst.getId(), cst.getName(), cst.getScores()
                ), (String) mapEntry.getValue());

        return entry;

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map.Entry<Customer, String> result = null;

        Map.Entry<Customer, String> customerStringEntry = customers.higherEntry(customer);

        if (customerStringEntry != null) {
            Customer cst = (Customer) customerStringEntry.getKey();
            result = new AbstractMap.SimpleEntry<>(
                    new Customer(cst.getId(), cst.getName(), cst.getScores()),
                    (String) customerStringEntry.getValue()
            );
        }
        return result;
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
