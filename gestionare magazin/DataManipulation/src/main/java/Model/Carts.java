package Model;

import BusinessLogic.ProductsBLL;

public class Carts implements Comparable{
    private int id_order;
    private int id_product;
    private float price_current;
    private int items_number;

    public Carts(int idOrder, int idProduct, float priceCurrent, int itemsNumber) {
        this.id_order = idOrder;
        this.id_product = idProduct;
        this.price_current = priceCurrent;
        this.items_number = itemsNumber;
    }

    public Carts() {}

    public float priceOfItems()
    {
        return this.price_current * items_number;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public float getPrice_current() {
        return price_current;
    }

    public void setPrice_current(float price_current) {
        this.price_current = price_current;
    }

    public int getItems_number() {
        return items_number;
    }

    public void setItems_number(int items_number) {
        this.items_number = items_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carts cart = (Carts) o;

        if (id_order != cart.getId_order()) return false;
        if (id_product != cart.getId_product()) return false;
        if (Float.compare(cart.getPrice_current(), price_current) != 0) return false;
        return items_number == cart.getItems_number();
    }

    @Override
    public int hashCode() {
        int result = id_order;
        result = 31 * result + id_product;
        result = 31 * result + (price_current != +0.0f ? Float.floatToIntBits(price_current) : 0);
        result = 31 * result + items_number;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Carts c=(Carts) o;
        return this.items_number-c.getItems_number();
    }

    @Override
    public String toString() {
        ProductsBLL pr = new ProductsBLL();
        Products product=null;
        try {
            product = pr.findProductById(this.id_product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return product.getName()+" "+this.items_number;
    }
}
