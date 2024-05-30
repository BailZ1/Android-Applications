package com.example.assignment4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUserActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, ageEditText;
    private TextView countryTextView, dobTextView;
    private Button selectCountryButton, selectDobButton, submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nameEditText = findViewById(R.id.enterName);
        emailEditText = findViewById(R.id.enterEmail);
        ageEditText = findViewById(R.id.enterAge);
        countryTextView = findViewById(R.id.countryTextView);
        dobTextView = findViewById(R.id.dobTextView);
        selectCountryButton = findViewById(R.id.selectCountryButton);
        selectDobButton = findViewById(R.id.selectDobButton);
        submitButton = findViewById(R.id.submitButton);

        selectCountryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountryDialog();
            }
        });

        selectDobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUserActivity.this, SelectDoBActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });
    }

    private void showCountryDialog() {
        final String[] countries = Data.getCountries();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Country")
                .setSingleChoiceItems(countries, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countryTextView.setText(countries[which]);
                    }
                })
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void handleSubmit() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String country = countryTextView.getText().toString();
        String dob = dobTextView.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
        } else if (age.isEmpty()) {
            Toast.makeText(this, "Age is required", Toast.LENGTH_SHORT).show();
        } else if (country.equals("N/A")) {
            Toast.makeText(this, "Country is required", Toast.LENGTH_SHORT).show();
        } else if (dob.equals("N/A")) {
            Toast.makeText(this, "Date of Birth is required", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(name, email, age, country, dob);
            Intent intent = new Intent(CreateUserActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String selectedDate = data.getStringExtra("selectedDate");
            dobTextView.setText(selectedDate);
        }
    }
}
