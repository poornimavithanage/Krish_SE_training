package lk.poornima.training.builder;

public class AppInitializer {
    public static void main(String[] args) {
        ItemTelescopic1 itemTelescopic1 = new ItemTelescopic1("I0023");
        System.out.println(itemTelescopic1);

        ItemTelescopic2 telescopic2 = new ItemTelescopic2("I0023");
        System.out.println(telescopic2);

        Item.Builder builder = new Item.Builder("I0023");
        Item item = builder.price(1500.00).build();
        System.out.println(item);
    }
}
