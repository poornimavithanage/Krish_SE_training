package lk.virtusa.training.chainofresponsibility;

public class Currency {
    /*store the amount to dispense handler */
    private int amount;

    public Currency(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
