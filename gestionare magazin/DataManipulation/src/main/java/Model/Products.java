package Model;

public class Products implements Comparable{
    private String name;
    private int stock;
    private int id_product;
    private float price;
    public Products(){};

    public Products(int idProduct, String name, float price, int stock) {
        this.id_product = idProduct;
        this.name=name;
        this.price = price;
        this.stock = stock;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void getProductsFromStock(int numberOfProducts)
    {
        this.stock-=numberOfProducts;
    }

    public boolean checkStock()
    {
        if(this.stock>0)
            return true;
        return false;
    }
    @Override
    public int compareTo(Object o) {
        Products p=(Products) o;
        return this.id_product - p.getId_product();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Products product = (Products) o;

        if (getId_product() != product.getId_product()) return false;
        if (Float.compare(product.getPrice(), getPrice()) != 0) return false;
        if (getStock() != product.getStock()) return false;
        return getName().equals(product.getName());
    }

    @Override
    public String toString() {
        return  name + "  " + stock;
    }

    @Override
    public int hashCode() {
        int result = getId_product();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getPrice() != +0.0f ? Float.floatToIntBits(getPrice()) : 0);
        result = 31 * result + getStock();
        return result;
    }
}
