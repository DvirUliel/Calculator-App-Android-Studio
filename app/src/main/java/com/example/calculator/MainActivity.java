package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    private double num1=0;
    private double num2=0;
    private char action='\0';
    private boolean isResultDisplayed=false;
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
        result = findViewById(R.id.View);
        result.setText("");
    }

    public void numFunction(View view) {
        Button button = (Button) view;
        String currentText = result.getText().toString();
        if(isResultDisplayed)
            return;
        // Prevent invalid inputs like multiple leading zeros
        if (button.getText().toString().equals("0") && currentText.equals("0")) {
            return; // Ignore additional zeroes
        }
        result.append(button.getText().toString());
    }


    public void actionFunction(View view) {
        isResultDisplayed=false;
        char ch = ((Button) view).getText().toString().charAt(0);
        String currentText = result.getText().toString();

        // Case 1: If the result is empty and the operator is '-', treat it as a negative number
        if (currentText.isEmpty() && ch == '-') {
            result.setText("-"); // Start a negative number
            return;
        }

        // Case 2: If there is already a number, store it as num1 and set the action
        if (!currentText.isEmpty()) {
            num1 = Double.parseDouble(currentText); // Store the first number
            action = ch; // Store the operator
            result.setText(""); // Clear the display
        }
    }


    public void equalsFunction(View view) {
        String currentText = result.getText().toString();

        // Ensure there is a second number to operate on
        if (!currentText.isEmpty()) {
            num2 = Double.parseDouble(currentText);
            switch (action) {
                case '+':
                    num1 += num2;
                    break;
                case '-':
                    num1 -= num2;
                    break;
                case 'x':
                    num1 *= num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        num1 /= num2;
                    } else {
                        result.setText("Error"); // Handle division by zero
                        return;
                    }
                    break;
            }
            result.setText(String.valueOf(num1));
            isResultDisplayed=true;
        }
    }

    public void clearFunction(View view) {
        num1 = 0;
        num2 = 0;
        action = '\0';
        result.setText("");
    }
}