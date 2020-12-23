package lk.virtusa.training.builder;

public class ItemTelescopic1 {
    String item_code;
    String description;
    String size;
    Double price;

    public ItemTelescopic1(String item_code) {
        this.item_code = item_code;
    }

    public ItemTelescopic1(String item_code, String description) {
        this(item_code); /*pass item_code into above constructor*/
        this.description = description;
    }

    public ItemTelescopic1(String item_code, String description, String size) {
        this(item_code, description);/*pass item_code,description into above constructor*/
        this.size = size;
    }

    public ItemTelescopic1(String item_code, String description, String size, Double price) {
        this(item_code, description, size); /*pass item_code,description, size into above constructor*/
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemTelescopic1{" +
                "item_code='" + item_code + '\'' +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }
}
