package lk.poornima.training.factory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PlaceOrder {
    private Map<ItemType,Item> items = new HashMap<>(); /*place the objects where you created*/

    public PlaceOrder(){
        this.initialize();
    }

    public Item getItem(ItemType itemType){
        Item item = null;

        try {
            item = (Item) items.get(itemType).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return item;
    }

    private void initialize() {
    Pizza pizza = new Pizza();
    pizza.setName("Mozzaralla");
    pizza.setQuantity(5);
    pizza.setItemCode("OD002");


    Pasta pasta = new Pasta();
    pasta.setPrice(BigDecimal.valueOf(1500.50));
    pasta.setItemCode("OD001");
    pasta.setQuantity(3);

    items.put(ItemType.PIZZA,pizza);
    items.put(ItemType.PASTA,pasta);
    }
}
