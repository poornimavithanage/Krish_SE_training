package lk.virtusa.training.factory;

import java.math.BigDecimal;

public class Pasta extends Item{

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pasta{" +
                "price=" + price +
                '}';
    }
}
