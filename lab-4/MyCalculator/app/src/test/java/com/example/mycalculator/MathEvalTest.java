package com.example.mycalculator;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for math evaluation.
 * <b>Note:</b> Since we only have one method with logic (evaluate),
 * we have created multiple tests for inputs to the same method.
 */
public class MathEvalTest {
    @Test
    public void evaluate_basicAddition_works() {
        assertEquals("4", MathEval.evaluate("2+2"));
    }

    @Test
    public void evaluate_basicSubtraction_works() {
        assertEquals("0", MathEval.evaluate("2-2"));
    }

    @Test
    public void evaluate_basicMultiplication_works() {
        assertEquals("12", MathEval.evaluate("3*4"));
    }

    @Test
    public void evaluate_basicDivision_works() {
        assertEquals("4", MathEval.evaluate("8/2"));
    }

    @Test
    public void evaluate_orderOfOperations_works() {
        assertEquals("14", MathEval.evaluate("2+3*4"));
    }
}