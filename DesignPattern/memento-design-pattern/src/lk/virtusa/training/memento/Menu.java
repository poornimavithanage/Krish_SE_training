package lk.virtusa.training.memento;

public class Menu {
    String name;

    public Menu(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                '}';
    }
}
