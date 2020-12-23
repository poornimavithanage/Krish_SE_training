package lk.virtusa.training.chainofresponsibility;

public class TwoHundredRupeesDispenseHandler implements DispenseHandlerChain{

    //concrete implementation
    private DispenseHandlerChain chain;

    @Override
    public void setNextChain(DispenseHandlerChain nextChain) {
        this.chain=nextChain;
    }

    @Override
    public void dispense(Currency currency) {
        if (currency.getAmount() >=200){
            int number = currency.getAmount() / 200;
            int remainder = currency.getAmount()%200;
            System.out.println("Dispensing Handler " +number+ "Rs.200");
            if(remainder!=0)
                this.chain.dispense(new Currency(remainder));
        }else{
            this.chain.dispense(currency);
        }
    }
}
