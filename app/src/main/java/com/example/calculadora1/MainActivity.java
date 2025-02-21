package com.example.conversorunidadesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private TextView resultText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Opciones de temperatura
        String[] options = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        convertButton.setOnClickListener(v -> convertTemperature());
    }

    private void convertTemperature() {
        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();
        double value;

        try {
            value = Double.parseDouble(inputValue.getText().toString());
        } catch (NumberFormatException e) {
            resultText.setText("Ingrese un número válido");
            return;
        }

        double result = 0.0;

        // Conversión de Celsius
        if (fromUnit.equals("Celsius")) {
            if (toUnit.equals("Fahrenheit")) {
                result = (value * 9/5) + 32;
            } else if (toUnit.equals("Kelvin")) {
                result = value + 273.15;
            } else {
                result = value;
            }
        }
        // Conversión de Fahrenheit
        else if (fromUnit.equals("Fahrenheit")) {
            if (toUnit.equals("Celsius")) {
                result = (value - 32) * 5/9;
            } else if (toUnit.equals("Kelvin")) {
                result = (value - 32) * 5/9 + 273.15;
            } else {
                result = value;
            }
        }
        // Conversión de Kelvin
        else if (fromUnit.equals("Kelvin")) {
            if (toUnit.equals("Celsius")) {
                result = value - 273.15;
            } else if (toUnit.equals("Fahrenheit")) {
                result = (value - 273.15) * 9/5 + 32;
            } else {
                result = value;
            }
        }

        resultText.setText("Resultado: " + result);
    }
}
