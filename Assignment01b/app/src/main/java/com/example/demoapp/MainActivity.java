package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        findViewById(R.id.button_calculate).setOnClickListener(this);
        findViewById(R.id.button_reset).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_calculate) {
            EditText inputTemperature = findViewById(R.id.input_temperature);
            String input = inputTemperature.getText().toString();
            if (!input.isEmpty()) {
                try {
                    double temperature = Double.parseDouble(input);
                    RadioGroup radioGroup = findViewById(R.id.radio_group_conversion);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    double result;
                    if (selectedId == R.id.radioButton_c_to_f) {
                        result = (temperature * 9 / 5) + 32; // Celsius to Fahrenheit
                    } else {
                        result = (temperature - 32) * 5 / 9; // Fahrenheit to Celsius
                    }
                    TextView results = findViewById(R.id.results);
                    results.setText(String.valueOf(result));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number entered", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Please enter a temperature", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.button_reset) {
            EditText inputTemperature = findViewById(R.id.input_temperature);
            inputTemperature.setText("");
            TextView results = findViewById(R.id.results);
            results.setText("N/A");
            RadioGroup radioGroup = findViewById(R.id.radio_group_conversion);
            radioGroup.clearCheck();
        }
    }
}
