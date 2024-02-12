package Model;

public record Bills(int id_order, int id_client, int id_product, float total_price, String date_trans) {

    @Override
    public String toString() {
        return "Order: "+this.id_order+" Client: "+this.id_client+" Product: "+this.id_product+" price: "+this.total_price+" time: "+this.date_trans;
    }
}