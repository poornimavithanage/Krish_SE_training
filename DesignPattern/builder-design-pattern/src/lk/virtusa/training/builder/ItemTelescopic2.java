package lk.virtusa.training.builder;

public class ItemTelescopic2 {
    String item_code;
    String description;
    String size;
    Double price;

    public ItemTelescopic2(String item_code, String description, String size, Double price) {
        this.item_code = item_code;
        this.description = description;
        this.size = size;
        this.price = price;
    }

    public ItemTelescopic2(String item_code, String description, String size) {
        this(item_code,description,size,null);

    }

    public ItemTelescopic2(String item_code, String description) {
        this(item_code,description,null);
    }

    public ItemTelescopic2(String item_code) {
        this(item_code,null);
    }

    @Override
    public String toString() {
        return "ItemTelescopic2{" +
                "item_code='" + item_code + '\'' +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }
}
