abstract public class BinaryExpression implements Expression{

    public Expression lft;
    public Expression rht;
    public String op;

    public BinaryExpression(Expression lft, Expression rht, String op) {
        this.lft = lft;
        this.rht = rht;
        this.op = op;
    }
    public String toString()
    {
        return "(" + lft + " " + op + " " + rht + ")";
    }

    public double evaluate(final Bindings bindings) {

        return applyOperator(lft.evaluate(bindings), rht.evaluate(bindings));
    }

    abstract protected double applyOperator(double lftBind, double rhtBind);
}
