package lk.poornima.training.factory;

public class WireTransferPayment extends Payment{
    @Override
    protected void makePayment() {
        universities.add(new Psychology());
    }
}
