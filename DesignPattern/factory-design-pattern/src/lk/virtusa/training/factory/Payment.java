package lk.virtusa.training.factory;

import java.util.ArrayList;
import java.util.List;

public abstract class Payment {
    protected List<University> universities=new ArrayList<>();

    public Payment(){
        makePayment();
    }

    protected abstract void makePayment();

    @Override
    public String toString() {
        return "Payment{" +
                "universities=" + universities +
                '}';
    }
}
