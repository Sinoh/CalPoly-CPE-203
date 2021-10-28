
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**
 * A small data holder class for a session.
 */
public class Session {

    public final String id;
    public final Customer customer;
    public final List<View> views;
    public final List<Buy> buys;
    //
    // And any other needed fields
    //

    public Session(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.views = new ArrayList<View>();
        this.buys = new ArrayList<Buy>();
    }

    public List getViews() {
        return this.views;
    }

    public void addViews(View view) {
        views.add(view);

    }

    public List getBuys() {
        return this.buys;
    }

    public void addBuys(Buy buy) {
        buys.add(buy);
    }

    /**
     * toString; useful for debugging
     */
    @Override
    public String toString() {
        return "Session(id=" + id + ", customer=" + customer + ")";
    }
}


