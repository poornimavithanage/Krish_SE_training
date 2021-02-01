package lk.poornima.training.factory;

import java.math.BigDecimal;

public class Application {
    public static void main(String[] args) {

        PlaceOrder placeOrder = new PlaceOrder();

        Pizza pizza = (Pizza) placeOrder.getItem(ItemType.PIZZA);
        System.out.println(pizza);

        pizza.setName("Cheese Lovers");
        System.out.println(pizza);

        Pizza pizza1 = (Pizza) placeOrder.getItem(ItemType.PIZZA);
        System.out.println(pizza1);

        System.out.println("===================================");

        Pasta pasta = (Pasta) placeOrder.getItem(ItemType.PASTA);
        System.out.println(pasta);

        pasta.setPrice(BigDecimal.valueOf(2000.00));
        System.out.println(pasta);

        Pasta pasta1 = (Pasta) placeOrder.getItem(ItemType.PASTA);
        System.out.println(pasta1);

        /*no need to creat new key word since we have template object in registry and we clone it when we need it */

    }
}
