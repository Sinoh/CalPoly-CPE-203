class NegationExpression
    implements Expression
{
    private Expression opnd;
    public NegationExpression(final Expression opnd)
    {

        this.opnd = opnd;
    }

    public String toString()
    {
        return "-" + opnd;
    }

    public double evaluate(final Bindings bindings)
    {
        return -opnd.evaluate(bindings);
    }


}
