package homework;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    Map<Customer, String> customers = new TreeMap<>(new CustomerComporator());

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map<Customer, String> customers2 = copyCutomers();

        Set<Map.Entry<Customer, String>> set2 = customers2.entrySet();
        Iterator<Map.Entry<Customer, String>> iterator = set2.iterator();
        Map.Entry mapEntry = (Map.Entry) iterator.next();

        return mapEntry;

    }

    private Map<Customer, String> copyCutomers() {
        Set<Map.Entry<Customer, String>> set = customers.entrySet();
        Iterator<Map.Entry<Customer, String>> iterator1 = set.iterator();

        Map<Customer, String> customers2 = new TreeMap<>(new CustomerComporator());
        while (iterator1.hasNext()){
            Map.Entry mapEntry = (Map.Entry) iterator1.next();
            Customer cs = (Customer) mapEntry.getKey();
            customers2.put(new Customer(cs.getId(), cs.getName(), cs.getScores()), (String) mapEntry.getValue());
        }
        return customers2;
    }


    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map<Customer, String> customers2 = copyCutomers();

        Set<Map.Entry<Customer, String>> set = customers2.entrySet();
        Iterator<Map.Entry<Customer, String>> iterator = set.iterator();

        Map.Entry<Customer, String> result = null;

        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            Customer cstmEntry = (Customer) mapEntry.getKey();
            if (cstmEntry.getScores() > customer.getScores()){
                result = mapEntry;
                break;
            }
        }
        return result;
    }

    public void add(Customer customer, String data) {
        customers.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }
}
