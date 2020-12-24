package lk.virtusa.training.memento;

import java.util.ArrayList;

//Originator
public class PlaceOrder {

    ArrayList<Menu>menus = new ArrayList<>();

    public void addMenu(Menu menu){
        menus.add(menu);
    }

    //next modification does not impact by using clone
    public ArrayList<Menu> getMenus() {
        return (ArrayList)menus.clone() ;
    }

    public PlaceOrderMemento save(){
        return new PlaceOrderMemento(getMenus());
    }

    //revert
    public void revert(PlaceOrderMemento placeOrderMemento){
        menus = placeOrderMemento.getMenus();
    }

    @Override
    public String toString() {
        return "PlaceOrder{" +
                "menus=" + menus +
                '}';
    }

    //inner class
    static class PlaceOrderMemento{
        ArrayList<Menu>menus;

    public PlaceOrderMemento(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    //placeOrder can only access get Menus
    public ArrayList<Menu> getMenus() {
        return menus;
    }
}
}
