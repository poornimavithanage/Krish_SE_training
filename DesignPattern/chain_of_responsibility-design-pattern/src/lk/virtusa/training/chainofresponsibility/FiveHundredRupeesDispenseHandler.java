package lk.virtusa.training.chainofresponsibility;

public class FiveHundredRupeesDispenseHandler implements DispenseHandlerChain{

    //concrete implementation
    private DispenseHandlerChain chain;
    @Override
    public void setNextChain(DispenseHandlerChain nextChain) {
        this.chain=nextChain;
    }

    @Override
    public void dispense(Currency currency) {
        if (currency.getAmount() >=500){
            int number = currency.getAmount() / 500;
            int remainder = currency.getAmount()%500;
            System.out.println("Dispensing Handler " + number + " Rs.500");
            if(remainder!=0)
                this.chain.dispense(new Currency(remainder));
        }else{
            this.chain.dispense(currency);
        }
    }
}
