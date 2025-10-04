package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn_dot, btn_clear, btn_plus, btn_minus, btn_mult, btn_div, btn_equal;
    TextView text_display;
    boolean justEvaluated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btn_dot = (Button) findViewById(R.id.btn_dot);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_mult = (Button) findViewById(R.id.btn_mult);
        btn_div = (Button) findViewById(R.id.btn_div);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        text_display = (TextView) findViewById(R.id.textview_input_display);

        setClickListeners();
    }

    private void setClickListeners() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn_dot.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_mult.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                addNumber("1");
                break;
            case R.id.btn2:
                addNumber("2");
                break;
            case R.id.btn3:
                addNumber("3");
                break;
            case R.id.btn4:
                addNumber("4");
                break;
            case R.id.btn5:
                addNumber("5");
                break;
            case R.id.btn6:
                addNumber("6");
                break;
            case R.id.btn7:
                addNumber("7");
                break;
            case R.id.btn8:
                addNumber("8");
                break;
            case R.id.btn9:
                addNumber("9");
                break;
            case R.id.btn0:
                addNumber("0");
                break;
            case R.id.btn_dot:
                addNumber(".");
                break;
            case R.id.btn_plus:
                addNumber("+");
                break;
            case R.id.btn_minus:
                addNumber("-");
                break;
            case R.id.btn_mult:
                addNumber("*");
                break;
            case R.id.btn_div:
                addNumber("/");
                break;
            case R.id.btn_equal:
                String result = null;
                try {
                    result = evaluate(text_display.getText().toString());
                    text_display.setText(result);
                } catch (Exception e) {
                    text_display.setText("Error");
                    e.printStackTrace();
                }
                justEvaluated = true;
                break;
            case R.id.btn_clear:
                clearDisplay();
                break;
        }
    }

    private String evaluate(String expression) throws Exception {
        BigDecimal result = null;
        StringBuffer currentNum = new StringBuffer();
        byte lastOp = '+';

        // Get the bytes of the expression, then add a dummy operator at the end.
        // Doing this ensures that the last operand will be evaluated, whereas it otherwise wouldn't
        // since the evaluation logic only runs when a new operator is found in the string
        var expressionBytesOriginal = expression.getBytes(StandardCharsets.UTF_8);
        var expressionBytes = new byte[expressionBytesOriginal.length + 1];
        System.arraycopy(expressionBytesOriginal, 0, expressionBytes, 0, expressionBytesOriginal.length);
        expressionBytes[expressionBytes.length - 1] = '+';

        for (var chr : expressionBytes) {
            // Handle digits/decimal points
            if (('0' <= chr && chr <= '9') || (chr == '.')) {
                currentNum.append((char) chr);
                continue;
            }

            System.out.println(currentNum.toString());

            // If the result is unset, then use the first typed number to initialize it
            if (result == null && (chr != '-' || currentNum.length() > 0)) {
                result = new BigDecimal(currentNum.toString());
                currentNum.setLength(0);
                lastOp = chr;
                continue;
            }

            // Handle operators
            switch (lastOp) {
                // Apply the operation to the result, and reset the number
                case '+': {
                    BigDecimal currentNumDec = new BigDecimal(currentNum.toString());
                    result = result.add(currentNumDec);
                    currentNum.setLength(0);
                    break;
                }
                case '-': {
                    // If the current num string is empty, assume that this is for a negative number
                    if (currentNum.length() == 0) {
                        currentNum.append('-');
                        break;
                    }

                    BigDecimal currentNumDec = new BigDecimal(currentNum.toString());
                    result = result.subtract(currentNumDec);
                    currentNum.setLength(0);
                    break;
                }
                case '*': {
                    BigDecimal currentNumDec = new BigDecimal(currentNum.toString());
                    result = result.multiply(currentNumDec);
                    currentNum.setLength(0);
                    break;
                }
                case '/': {
                    BigDecimal currentNumDec = new BigDecimal(currentNum.toString());
                    result = result.divide(currentNumDec, RoundingMode.HALF_UP);
                    currentNum.setLength(0);
                    break;
                }
                default:
                    throw new Error("Unexpected input");
            }
            lastOp = chr;
        }

        return result.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toString();
    }

    private void addNumber(String number) {
        if (justEvaluated) {
            text_display.setText(number);
            justEvaluated = false;
            return;
        }
        text_display.setText(text_display.getText() + number);
    }

    private void clearDisplay() {
        text_display.setText("");
    }
}