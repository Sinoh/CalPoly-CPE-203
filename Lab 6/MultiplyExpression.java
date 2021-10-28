class MultiplyExpression
        extends BinaryExpression
{
    public MultiplyExpression(Expression lft,  Expression rht, String op)
    {
        super(lft, rht, op);
    }

    public double applyOperator(double lftBind, double rhtBind) {
        return lftBind * rhtBind;
    }
}

