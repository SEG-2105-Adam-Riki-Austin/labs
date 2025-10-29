package com.example.mycalculator;

import static org.junit.Assert.*;

public class MathEvalTest {

    @org.junit.Test
    public void evalute_validInput_addition() {
        String result = MathEval.eval("2+2");
        assertEquals("4", result);
    }

    @org.junit.Test
    public void evalute_validInput_subtraction() {
        String result = MathEval.eval("4-2");
        assertEquals("2", result);
    }
}