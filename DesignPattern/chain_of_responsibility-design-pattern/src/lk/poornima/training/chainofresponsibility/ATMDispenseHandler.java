package lk.poornima.training.chainofresponsibility;

import java.util.Scanner;

public class ATMDispenseHandler {

   private DispenseHandlerChain ch1;

   public ATMDispenseHandler(){
       //initialize the chain
       this.ch1 = new FiveHundredRupeesDispenseHandler();
       DispenseHandlerChain ch2 = new TwoHundredRupeesDispenseHandler();
       DispenseHandlerChain ch3 = new OneHundredRupeesDispenseHandler();

       //set the chain responsibility
       ch1.setNextChain(ch2);
       ch2.setNextChain(ch3);
   }



    public static void main(String[] args) {
        ATMDispenseHandler atmDispenseHandler = new ATMDispenseHandler();
        while (true){
            int amount = 0;
            System.out.println("Enter amount to ATM Dispenser");
            Scanner scanner = new Scanner(System.in);
            amount = scanner.nextInt();
            if(amount % 100 !=0){
                System.out.println("Amount should be in multiple of 100s");
                return;
            }
            //process the request
            atmDispenseHandler.ch1.dispense(new Currency(amount));
        }
    }




}
