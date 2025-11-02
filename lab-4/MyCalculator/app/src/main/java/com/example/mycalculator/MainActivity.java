package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDot, btnClear, btnPlus, btnMinus, btnMult, btnDiv, btnEqual;
    TextView textDisplay;
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
        btnDot = (Button) findViewById(R.id.btn_dot);
        btnPlus = (Button) findViewById(R.id.btn_plus);
        btnMinus = (Button) findViewById(R.id.btn_minus);
        btnMult = (Button) findViewById(R.id.btn_mult);
        btnDiv = (Button) findViewById(R.id.btn_div);
        btnEqual = (Button) findViewById(R.id.btn_equal);
        btnClear = (Button) findViewById(R.id.btn_clear);
        textDisplay = (TextView) findViewById(R.id.textview_input_display);

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
        btnDot.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                addNumber('1');
                break;
            case R.id.btn2:
                addNumber('2');
                break;
            case R.id.btn3:
                addNumber('3');
                break;
            case R.id.btn4:
                addNumber('4');
                break;
            case R.id.btn5:
                addNumber('5');
                break;
            case R.id.btn6:
                addNumber('6');
                break;
            case R.id.btn7:
                addNumber('7');
                break;
            case R.id.btn8:
                addNumber('8');
                break;
            case R.id.btn9:
                addNumber('9');
                break;
            case R.id.btn0:
                addNumber('0');
                break;
            case R.id.btn_dot:
                addNumber('.');
                break;
            case R.id.btn_plus:
                addNumber('+');
                break;
            case R.id.btn_minus:
                addNumber('-');
                break;
            case R.id.btn_mult:
                addNumber('*');
                break;
            case R.id.btn_div:
                addNumber('/');
                break;
            case R.id.btn_equal:
                String result = null;
                try {
                    result = MathEval.evaluate(textDisplay.getText().toString());
                    textDisplay.setText(result);
                } catch (Exception e) {
                    textDisplay.setText("Error");
                    e.printStackTrace();
                }
                justEvaluated = true;
                break;
            case R.id.btn_clear:
                clearDisplay();
                break;
        }
    }

    private void addNumber(char number) {
        // If the user presses a new number after a calculation,
        // assume that they want to start a new one
        if (justEvaluated) {
            justEvaluated = false;
            if ('0' <= number && number <= '9') {
                textDisplay.setText(Character.toString(number));
                return;
            }
        }
        textDisplay.setText(String.format("%s%c", textDisplay.getText(), number));
    }

    private void clearDisplay() {
        textDisplay.setText("");
    }
}
