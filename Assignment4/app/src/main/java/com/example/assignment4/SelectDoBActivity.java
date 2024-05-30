package com.example.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SelectDoBActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private Button submitButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dob);

        datePicker = findViewById(R.id.datePicker);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        datePicker.setMaxDate(calendar.getTimeInMillis());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                String selectedDate = day + "/" + (month + 1) + "/" + year;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedDate", selectedDate);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
