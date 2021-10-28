
public class Product {

    private String productId;
    private int price;

    public Product(String productId, int price) {
        this.productId = productId;
        this.price = price;
    }

    public String getProductId() {
        return this.productId;
    }

    public int getPrice() {
        return this.price;
    }

    public String toString() {
        return "Product(id=" + getProductId() + ")";
    }
}
