
// TODO:  Fill this in
public class CustomerID {

    private int id;

    public CustomerID (String customerRegion, String customerIDInRegion) {
        this.id = customerRegion.hashCode() * 31 + customerIDInRegion.hashCode() * 7;

    }

    public int getId() {
        return this.id;
    }

}
