package lk.poornima.training.memento;

public class Application {
    public static void main(String[] args) {
        CareTaker careTaker = new CareTaker();
        PlaceOrder placeOrder = new PlaceOrder();

        placeOrder.addMenu(new Menu("Cheese Lovers"));
        placeOrder.addMenu(new Menu("Mozzarella"));

        careTaker.save(placeOrder);
        System.out.println(placeOrder);

        placeOrder.addMenu(new Menu("Butter Chicken Masala"));
        careTaker.save(placeOrder);
        System.out.println(placeOrder);

        placeOrder.addMenu(new Menu("BBQ Chicken"));
        careTaker.save(placeOrder);
        System.out.println(placeOrder);

        placeOrder.addMenu(new Menu("Chicken Bacon"));
        careTaker.save(placeOrder);
        System.out.println(placeOrder);

        placeOrder.addMenu(new Menu("Hot Garlic Prawns"));
//        careTaker.save(placeOrder);
        System.out.println(placeOrder);

        careTaker.revert(placeOrder);
        System.out.println(placeOrder);

        placeOrder.addMenu(new Menu("Veggie Supreme"));
//        careTaker.save(placeOrder);
        System.out.println(placeOrder);

        careTaker.revert(placeOrder);
        System.out.println(placeOrder);

    }
}
