

public class MyProgram {

    public static void main(String[] args) {
        GraphRange range = new GraphRange(0.0, 2*Math.PI, -1.0, 1.0);
        Graphy graphy = new Graphy(range);
        GraphyFunction f = (x) -> { return Math.sin(x); };
        graphy.add(f);
        graphy.add((x) -> { return 0.2 * Math.tan(x); }, java.awt.Color.GREEN);

        graphy.run();
    }
    //
    // To do at home:
    //
    //   *  Add a new function to Graphy
    //   *  Do it each of the three ways we learned
}
