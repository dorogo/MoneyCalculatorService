package com.dorogo.MoneyCalculatorService.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyBigDecimal extends BigDecimal {

    private static MathContext context = new MathContext(2, RoundingMode.HALF_DOWN);

    public MyBigDecimal(String s) {
        super(s, context);
    }
    public MyBigDecimal(BigDecimal bd) {
        this(bd.toString());
    }
    public MyBigDecimal(int v) {
        super(""+v, context);
    }

    public MyBigDecimal divide( BigDecimal divisor ){
        return new MyBigDecimal( super.divide( divisor, context ) );
    }

    public MyBigDecimal add(BigDecimal augend ){
        return new MyBigDecimal( super.add( augend, context ) );
    }

    public MyBigDecimal subtract(MyBigDecimal subtrahend) {
        return new MyBigDecimal(super.subtract(subtrahend, context));
    }

    public MyBigDecimal abs() {
        return new MyBigDecimal(super.abs());
    }

    public MyBigDecimal negate() {
        return new MyBigDecimal(super.negate());
    }
}