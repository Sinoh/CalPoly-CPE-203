public class Buy {

    private String sessionId;
    private String productId;
    private int price;
    private int quantity;

    public Buy(String sessionId, String productId, int price, int quantity) {
        this.sessionId = sessionId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
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

    public int getQuantity() {
        return this.quantity;
    }



}
