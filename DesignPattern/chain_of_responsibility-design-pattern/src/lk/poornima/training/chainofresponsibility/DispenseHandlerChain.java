package lk.poornima.training.chainofresponsibility;

public interface DispenseHandlerChain {

    /*define next processor in the chain and method will process the request */

    void setNextChain(DispenseHandlerChain nextChain);

    void dispense(Currency currency);
}
