import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class LogAnalyzer {

    private final Log log;    // The log we're analyzing

    /**
     * Create a new analyzer object to analyze the given log
     */
    public LogAnalyzer(Log log) {
        this.log = log;
    }

        //write this after you have figured out how to store your data
        //make sure that you understand the problem
    void printAverageViewsWithoutPurchase() {
        float count = 0;
        for (Session session : log.sessionByID.values()) {
            count += session.getViews().size();
        }
        System.out.println("Average Views without Purchase: " + count/log.sessionByID.size() + "\n");
    }

        //write this after you have figured out how to store your data
        //make sure that you understand the problem
    void printSessionPriceDifference() {
        System.out.println("Price Difference for Purchased Product by Session");

        List<String> key = new ArrayList<>();
        List<Session> value = new ArrayList<>();

        for (String keyset : log.sessionByID.keySet()) {
            key.add(keyset);
        }

        Collections.reverse(key);

        for (Session session : log.sessionByID.values()) {
            value.add(session);
        }

        for (int i = 0; i < key.size(); i++) {
            int avg = 0;
            Session session = log.sessionByID.get(key.get(i));
            for (View view : session.views) {
                avg += view.getPrice();
        }

        avg = avg/session.views.size();
        List<Buy> buys = session.buys;
        if (buys.size() > 0) {
            System.out.println("    " + session.id);
            for (Buy buy : buys) {
                int avgPrice = buy.getPrice() - avg;
                System.out.println("        " + buy.getProductId() + " " + avgPrice);
            }
        }

    }
        /* add printing */
        // Hint:  ArrayList.sort() is a good way to sort a list.  See also
        //        https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html#ArrayList-java.util.Collection-
    }

        //write this after you have figured out how to store your data
        //make sure that you understand the problem
    void printCustomerItemViewsForPurchase() {
        System.out.println("\nNumber of Views for Purchased Product by Customer");
        for (Session session : log.sessionByID.values()) {
            if (session.getBuys().size() > 0) {
                System.out.println("    " + session.customer.getCustomerRegion() + " " + session.customer.getCustomerIDInRegion());
                for (Buy buys : session.buys) {
                    int count = 0;
                    for (View views : session.views) {
                        if (buys.getProductId().equals(views.getProductId())) {
                            count += 1;
                        }
                    }
                    System.out.println("        " + buys.getProductId() + " " + count);
                }
            }
        }
    }


    /*
     * This method traverses your Log once it is populated.
     */
    private void printOutExample() {
        //
        // Iterate through the customers, in any order
        //
        for (Customer customer : log.customerByID.values()) {
            System.out.println(customer);
            for (Session session : customer.getSessions()) {
                System.out.println("    in " + session);
                for (View view : session.views) {
                    System.out.println("        looked at " + view.getProduct());
                }
            }
        }
    }

    /**
     * The main analyzing function
     */
    public void analyze() {
        if (Constants.DEBUG) {
            System.out.println();
            System.out.println("*******  printOutExample() results:  ************");
            System.out.println();
            printOutExample();
            System.out.println();
            System.out.println();
        }
        printAverageViewsWithoutPurchase();
        printSessionPriceDifference();
        printCustomerItemViewsForPurchase();

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Log file(s) not specified.");
            System.exit(1);
        }

        try {
            for (String fileName : args) {
                LogParser parser = new LogParser(new File(fileName));
                LogAnalyzer analyzer = new LogAnalyzer(parser.parse());
                analyzer.analyze();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
