
public class View {

    private String sessionId;
    private String productId;
    private int price;
    private Product product;

    public View(String sessionId, String productId, int price) {
        this.sessionId = sessionId;
        this.productId = productId;
        this.price = price;
        this.product = new Product(productId, price);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getProductId() {
        return this.productId;
    }

    public int getPrice() {
        return this.price;
    }

    public Product getProduct() {
        return product;
    }

}
