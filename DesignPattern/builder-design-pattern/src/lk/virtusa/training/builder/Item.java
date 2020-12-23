package lk.virtusa.training.builder;

public class Item {
    private final String item_code;
    private final String description;
    private final String size;
    private final Double price;

    public Item(Builder builder) {
        this.item_code = builder.item_code;
        this.description = builder.description;
        this.size = builder.size;
        this.price = builder.price;
    }


    static class Builder{    //Builder class
        private String item_code;
        private String description;
        private String size;
        private Double price;

        //create build method
        public Item build(){
            return new Item(this);
        }

        public Builder(String item_code){
            this.item_code = item_code;

        }

        public Builder description(String description) {
            this.description = description;
            return this;     //return current instance
        }

        public Builder size(String size) {
            this.size = size;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "item_code='" + item_code + '\'' +
                    ", description='" + description + '\'' +
                    ", size='" + size + '\'' +
                    ", price=" + price +
                    '}';
        }
    }



}
