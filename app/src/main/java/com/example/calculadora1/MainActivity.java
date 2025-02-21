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
    private Spinner spinnerCategory, spinnerFrom, spinnerTo;
    private TextView resultText;
    private Button convertButton;

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

        String[] categories = {"Temperatura", "Moneda", "Longitud"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(categoryAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSpinners(categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        convertButton.setOnClickListener(v -> convertUnits());
    }

    private void updateSpinners(String category) {
        String[] options;
        switch (category) {
            case "Temperatura":
                options = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
                break;
            case "Moneda":
                options = new String[]{"Dólar", "Euro", "Sol"};
                break;
            case "Longitud":
                options = new String[]{"Metro", "Kilómetro", "Milla"};
                break;
            default:
                options = new String[]{};
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
    }

    private void convertUnits() {
        String category = spinnerCategory.getSelectedItem().toString();
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

        switch (category) {
            case "Temperatura":
                result = convertTemperature(value, fromUnit, toUnit);
                break;
            case "Moneda":
                result = convertCurrency(value, fromUnit, toUnit);
                break;
            case "Longitud":
                result = convertLength(value, fromUnit, toUnit);
                break;
        }

        resultText.setText("Resultado: " + result);
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals(to)) return value;
        if (from.equals("Celsius") && to.equals("Fahrenheit")) return (value * 9/5) + 32;
        if (from.equals("Celsius") && to.equals("Kelvin")) return value + 273.15;
        if (from.equals("Fahrenheit") && to.equals("Celsius")) return (value - 32) * 5/9;
        if (from.equals("Fahrenheit") && to.equals("Kelvin")) return (value - 32) * 5/9 + 273.15;
        if (from.equals("Kelvin") && to.equals("Celsius")) return value - 273.15;
        if (from.equals("Kelvin") && to.equals("Fahrenheit")) return (value - 273.15) * 9/5 + 32;
        return value;
    }

    private double convertCurrency(double value, String from, String to) {
        double usdToEur = 0.92, usdToSol = 3.80;
        if (from.equals("Dólar") && to.equals("Euro")) return value * usdToEur;
        if (from.equals("Dólar") && to.equals("Sol")) return value * usdToSol;
        if (from.equals("Euro") && to.equals("Dólar")) return value / usdToEur;
        if (from.equals("Euro") && to.equals("Sol")) return (value / usdToEur) * usdToSol;
        if (from.equals("Sol") && to.equals("Dólar")) return value / usdToSol;
        if (from.equals("Sol") && to.equals("Euro")) return (value / usdToSol) * usdToEur;
        return value;
    }

    private double convertLength(double value, String from, String to) {
        if (from.equals(to)) return value;
        if (from.equals("Metro") && to.equals("Kilómetro")) return value / 1000;
        if (from.equals("Metro") && to.equals("Milla")) return value / 1609.34;
        if (from.equals("Kilómetro") && to.equals("Metro")) return value * 1000;
        if (from.equals("Kilómetro") && to.equals("Milla")) return value / 1.60934;
        if (from.equals("Milla") && to.equals("Metro")) return value * 1609.34;
        if (from.equals("Milla") && to.equals("Kilómetro")) return value * 1.60934;
        return value;
    }
}
