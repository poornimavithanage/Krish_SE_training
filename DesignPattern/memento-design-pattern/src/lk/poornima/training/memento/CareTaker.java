package lk.poornima.training.memento;

import java.util.Stack;

public class CareTaker {
    Stack<PlaceOrder.PlaceOrderMemento>history = new Stack<>();

    public void save(PlaceOrder placeOrder){
        history.push(placeOrder.save());
    }

    public void revert(PlaceOrder placeOrder){
        if(!history.empty())
            placeOrder.revert(history.pop());
        else
            System.out.println("CAN NOT UNDO");
    }
}
