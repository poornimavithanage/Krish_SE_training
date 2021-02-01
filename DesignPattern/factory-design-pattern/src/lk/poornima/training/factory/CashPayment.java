package lk.poornima.training.factory;

public class CashPayment extends Payment{
    @Override
    protected void makePayment() {
        universities.add(new Psychology());
        universities.add(new CivilEngineering());
    }
}
