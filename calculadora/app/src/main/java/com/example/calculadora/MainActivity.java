package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fathzer.soft.javaluator.DoubleEvaluator;

public class MainActivity extends AppCompatActivity {
    private TextView tvRes; // mostrar el resultat
    private StringBuilder expressio = new StringBuilder(); // ex: "33+5+15"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvRes = findViewById(R.id.tvRes);
        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btnDot = findViewById(R.id.buttonDot);

        Button btnMod = findViewById(R.id.buttonModule);
        Button btnCParentesis = findViewById(R.id.buttonCloseParentesis);
        Button btnOParentesis = findViewById(R.id.buttonOpenParentesis);
        Button btnMinus = findViewById(R.id.buttonMinus);
        Button btnPlus = findViewById(R.id.buttonPlus);
        Button btnMulti = findViewById(R.id.buttonMultiplication);
        Button btnDiv = findViewById(R.id.buttonDivision);
        Button btnReset = findViewById(R.id.buttonReset);
        Button btnEquals = findViewById(R.id.buttonEquals);

        // Listeners
        btn0.setOnClickListener(v -> afegirNum("0"));
        btn1.setOnClickListener(v -> afegirNum("1"));
        btn2.setOnClickListener(v -> afegirNum("2"));
        btn3.setOnClickListener(v -> afegirNum("3"));
        btn4.setOnClickListener(v -> afegirNum("4"));
        btn5.setOnClickListener(v -> afegirNum("5"));
        btn6.setOnClickListener(v -> afegirNum("6"));
        btn7.setOnClickListener(v -> afegirNum("7"));
        btn8.setOnClickListener(v -> afegirNum("8"));
        btn9.setOnClickListener(v -> afegirNum("9"));
        btnDot.setOnClickListener(v -> afegirNum("."));
        btnOParentesis.setOnClickListener(v -> afegirNum("("));
        btnCParentesis.setOnClickListener(v -> afegirNum(")"));

        btnMod.setOnClickListener(v -> operacio("%"));
        btnMinus.setOnClickListener(v -> operacio("-"));
        btnPlus.setOnClickListener(v -> operacio("+"));
        btnMulti.setOnClickListener(v -> operacio("*"));
        btnDiv.setOnClickListener(v -> operacio("/"));
        btnReset.setOnClickListener(v -> reset());
        btnEquals.setOnClickListener(v -> evaluar());

        actualitzar();
    }

    private void afegirNum(String c) {
        if (c.equals(".")) {
            int lastOpPos = expressio.length();
            for (int i = expressio.length() - 1; i >= 0; i--) {
                char ch = expressio.charAt(i);
                if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == '%') {
                    lastOpPos = i + 1;
                    break;
                }
            }
            String currentNum = expressio.substring(lastOpPos);
            if (currentNum.contains(".")) {
                return;
            }
            expressio.append(".");
        } else if (c.equals("(")) {
            if (expressio.length() == 0 ||
                    " +-*/(%".contains(String.valueOf(expressio.charAt(expressio.length() - 1)))) {
                expressio.append("(");
            }
        } else if (c.equals(")")) {
            if (expressio.length() > 0) {
                char last = expressio.charAt(expressio.length() - 1);
                if (Character.isDigit(last) || last == '.' || last == ')') {
                    int openCount = 0, closeCount = 0;
                    for (char ch : expressio.toString().toCharArray()) {
                        if (ch == '(') openCount++;
                        else if (ch == ')') closeCount++;
                    }
                    if (closeCount < openCount) {
                        expressio.append(")");
                    }
                }
            }
        } else {
            if (expressio.length() == 0 && c.equals("0")) {
                expressio.append("0");
            } else if (expressio.length() == 1 && expressio.toString().equals("0") && !c.equals("0")) {
                expressio.setLength(0);
                expressio.append(c);
            } else {
                expressio.append(c);
            }
        }
        tvRes.setText(expressio.toString());
    }

    private void operacio(String op) {
        if (expressio.length() == 0) {
            if (op.equals("-")) {
                expressio.append(op);
            }
            tvRes.setText(expressio.toString());
            return;
        }

        char ultimChar = expressio.charAt(expressio.length() - 1);
        if (Character.isDigit(ultimChar) || ultimChar == '.' || ultimChar == ')') {
            expressio.append(op);
        } else {
            if (!Character.isDigit(ultimChar) && ultimChar != '(' && ultimChar != ')') {
                expressio.setLength(expressio.length() - 1);
            }
            expressio.append(op);
        }
        tvRes.setText(expressio.toString());
    }

    private void evaluar() {
        // https://mvnrepository.com/artifact/com.fathzer/javaluator
        // https://github.com/fathzer/javaluator  3.0.6
        // "(2^3-1)*sin(pi/4)/ln(pi^2)" = 2.1619718020347976
        if (expressio.length() == 0) {
            tvRes.setText("0");
            return;
        }
        // evaluem
        try {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Double result = evaluator.evaluate(expressio.toString());

            tvRes.setText(result.toString());
            expressio.setLength(0);
        } catch (Exception e) {
            tvRes.setText("Error");
            expressio.setLength(0);
        }
    }

    private void reset() {
        expressio.setLength(0);
        tvRes.setText("0");
    }

    private void actualitzar() {
        if (expressio.length() == 0) {
            tvRes.setText("0");
        } else {
            tvRes.setText(expressio.toString());
        }
    }
}
