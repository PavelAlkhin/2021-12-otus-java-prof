package homework;

import java.util.Comparator;

public class CustomerComporator implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {
        if (o1.getScores() > o2.getScores()) {
            return 1;
        } else if (o1.getScores() < o2.getScores()) {
            return -1;
        } else {
            return 0;
        }
    }
}
