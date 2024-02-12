package Model;

public class Orders {
    private int id_order;
    private float total_price;
    private int id_client;

    public Orders() {
    }

    public Orders(int idClient, int idOrder, float totalPrice) {
        this.id_client = idClient;
        this.id_order = idOrder;
        this.total_price = totalPrice;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return id_order + "  "+ total_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders order = (Orders) o;

        if (getId_client() != order.getId_client()) return false;
        if (getId_order() != order.getId_order()) return false;
        return Float.compare(order.getTotal_price(), getTotal_price()) == 0;
    }

    @Override
    public int hashCode() {
        int result = getId_client();
        result = 31 * result + getId_order();
        result = 31 * result + (getTotal_price() != +0.0f ? Float.floatToIntBits(getTotal_price()) : 0);
        return result;
    }

}
