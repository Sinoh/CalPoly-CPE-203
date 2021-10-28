
import java.util.ArrayList;
import java.util.List;

// TODO:  Fill this in

public class Customer {

    private String customerRegion;
    private String customerIDInRegion;
    private List<Session> sessions;
    private CustomerID customerId;

    public Customer(String customerRegion, String customerIDInRegion) {
        this.customerRegion = customerRegion;
        this.customerIDInRegion = customerIDInRegion;
        this.sessions = new ArrayList<Session>();
        this.customerId = new CustomerID(customerRegion, customerIDInRegion);
    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public String getCustomerRegion() {
        return this.customerRegion;
    }

    public String getCustomerIDInRegion() {
        return this.customerIDInRegion;
    }

    public CustomerID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "Customer(region=" + customerRegion + ", idInRegion=" + customerIDInRegion + ")";
    }
}
