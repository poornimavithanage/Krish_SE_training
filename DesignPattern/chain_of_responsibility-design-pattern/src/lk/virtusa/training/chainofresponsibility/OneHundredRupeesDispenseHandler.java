package lk.virtusa.training.chainofresponsibility;

public class OneHundredRupeesDispenseHandler implements DispenseHandlerChain{

    private DispenseHandlerChain chain;

    @Override
    public void setNextChain(DispenseHandlerChain nextChain) {
        this.chain=nextChain;
    }

    @Override
    public void dispense(Currency currency) {
        if (currency.getAmount() >=100){
            int number = currency.getAmount() / 100;
            int remainder = currency.getAmount()%100;
            System.out.println("Dispensing Handler " +number+ " Rs.100");
            if(remainder!=0)
                this.chain.dispense(new Currency(remainder));
        }else{
            this.chain.dispense(currency);
        }
    }
}
