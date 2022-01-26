package homework;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

//    Map<Customer, String> customers = new TreeMap<>(new CustomerComporator());

    NavigableMap<Customer, String> customers = new TreeMap<Customer, String>((o1, o2) -> Long.compare(o1.getScores(), o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
//        Map<Customer, String> customers2 = copyCutomers();

        Set<Map.Entry<Customer, String>> set = customers.entrySet();
        Iterator<Map.Entry<Customer, String>> iterator = set.iterator();
        Map.Entry mapEntry = (Map.Entry) iterator.next();
        Customer cst = (Customer) mapEntry.getKey();

        Map.Entry<Customer, String> entry =
                new AbstractMap.SimpleEntry<>(new Customer(
                        cst.getId(), cst.getName(), cst.getScores()
                ), (String) mapEntry.getValue());

        return entry;

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Set<Map.Entry<Customer, String>> set = customers.entrySet();
        Iterator<Map.Entry<Customer, String>> iterator = set.iterator();

        Map.Entry<Customer, String> result = null;

        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            Customer cstmEntry = (Customer) mapEntry.getKey();
            if (cstmEntry.getScores() > customer.getScores()) {
                Customer cst = (Customer) mapEntry.getKey();
                result = new AbstractMap.SimpleEntry<>(
                        new Customer(cst.getId(), cst.getName(), cst.getScores()),
                        (String) mapEntry.getValue()
                );
                break;
            }
        }
        return result;
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
