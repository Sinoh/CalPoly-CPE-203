class DivideExpression
    extends BinaryExpression
{
    public DivideExpression(Expression lft,  Expression rht, String op)
    {
        super(lft, rht, op);
    }

    public double applyOperator(double lftBind, double rhtBind) {
        return lftBind / rhtBind;
    }
}

