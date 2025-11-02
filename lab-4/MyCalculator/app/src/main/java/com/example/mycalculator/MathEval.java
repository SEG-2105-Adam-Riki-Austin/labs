package com.example.mycalculator;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathEval {
    private MathEval() {}

    public static String evaluate(String expression) {
        Expression exp = new ExpressionBuilder(expression).build();
        BigDecimal result = BigDecimal.valueOf(exp.evaluate());
        return result.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }
}