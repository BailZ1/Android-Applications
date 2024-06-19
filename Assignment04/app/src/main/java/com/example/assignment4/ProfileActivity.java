package com.example.assignment4;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTextView, emailTextView, ageTextView, countryTextView, dobTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        ageTextView = findViewById(R.id.ageTextView);
        countryTextView = findViewById(R.id.countryTextView);
        dobTextView = findViewById(R.id.dobTextView);

        User user = (User) getIntent().getSerializableExtra("user");

        if (user != null) {
            nameTextView.setText("Name: " + user.getName());
            emailTextView.setText("Email: " + user.getEmail());
            ageTextView.setText("Age: " + user.getAge());
            countryTextView.setText("Country: " + user.getCountry());
            dobTextView.setText("DoB: " + user.getDob());
        }
    }
}
