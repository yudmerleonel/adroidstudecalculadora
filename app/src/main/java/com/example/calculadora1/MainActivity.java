package com.example.conversor;

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
    private Spinner spinnerCategory, spinnerFrom, spinnerTo;
    private Button convertButton;
    private TextView resultText;

    private String[] categories = {"Temperatura", "Moneda", "Longitud"};
    private String[][] units = {
            {"Celsius", "Fahrenheit", "Kelvin"},
            {"USD", "EUR", "PEN"},
            {"Metros", "Kilómetros", "Centímetros"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Adaptador para categorías
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(categoryAdapter);

        // Cambiar unidades según la categoría seleccionada
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, units[position]);
                spinnerFrom.setAdapter(unitAdapter);
                spinnerTo.setAdapter(unitAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Botón de conversión
        convertButton.setOnClickListener(v -> convertUnits());
    }

    private void convertUnits() {
        String category = spinnerCategory.getSelectedItem().toString();
        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();
        String input = inputValue.getText().toString();

        if (input.isEmpty()) {
            resultText.setText("Ingrese un valor");
            return;
        }

        double value = Double.parseDouble(input);
        double result = 0;

        if (category.equals("Temperatura")) {
            result = convertTemperature(value, fromUnit, toUnit);
        } else if (category.equals("Moneda")) {
            result = convertCurrency(value, fromUnit, toUnit);
        } else if (category.equals("Longitud")) {
            result = convertLength(value, fromUnit, toUnit);
        }

        resultText.setText("Resultado: " + result);
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals(to)) return value;
        if (from.equals("Celsius") && to.equals("Fahrenheit")) return (value * 9/5) + 32;
        if (from.equals("Celsius") && to.equals("Kelvin")) return value + 273.15;
        if (from.equals("Fahrenheit") && to.equals("Celsius")) return (value - 32) * 5/9;
        if (from.equals("Fahrenheit") && to.equals("Kelvin")) return ((value - 32) * 5/9) + 273.15;
        if (from.equals("Kelvin") && to.equals("Celsius")) return value - 273.15;
        if (from.equals("Kelvin") && to.equals("Fahrenheit")) return ((value - 273.15) * 9/5) + 32;
        return value;
    }

    private double convertCurrency(double value, String from, String to) {
        if (from.equals(to)) return value;
        double usdToEur = 0.92, usdToPen = 3.7, eurToUsd = 1.08, eurToPen = 4, penToUsd = 0.27, penToEur = 0.25;
        if (from.equals("USD") && to.equals("EUR")) return value * usdToEur;
        if (from.equals("USD") && to.equals("PEN")) return value * usdToPen;
        if (from.equals("EUR") && to.equals("USD")) return value * eurToUsd;
        if (from.equals("EUR") && to.equals("PEN")) return value * eurToPen;
        if (from.equals("PEN") && to.equals("USD")) return value * penToUsd;
        if (from.equals("PEN") && to.equals("EUR")) return value * penToEur;
        return value;
    }

    private double convertLength(double value, String from, String to) {
        if (from.equals(to)) return value;
        if (from.equals("Metros") && to.equals("Kilómetros")) return value / 1000;
        if (from.equals("Metros") && to.equals("Centímetros")) return value * 100;
        if (from.equals("Kilómetros") && to.equals("Metros")) return value * 1000;
        if (from.equals("Kilómetros") && to.equals("Centímetros")) return value * 100000;
        if (from.equals("Centímetros") && to.equals("Metros")) return value / 100;
        if (from.equals("Centímetros") && to.equals("Kilómetros")) return value / 100000;
        return value;
    }
}
