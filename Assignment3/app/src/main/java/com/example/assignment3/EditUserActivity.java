package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUserActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private RadioGroup roleRadioGroup;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        nameEditText = findViewById(R.id.editName);
        emailEditText = findViewById(R.id.editEmail);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        Button submitButton = findViewById(R.id.submitButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        user = (User) getIntent().getSerializableExtra("user");

        if (user != null) {
            nameEditText.setText(user.getName());
            emailEditText.setText(user.getEmail());
            // Set the correct role
            int roleButtonId = -1;
            switch (user.getRole()) {
                case "Student":
                    roleButtonId = R.id.radioStudent;
                    break;
                case "Employee":
                    roleButtonId = R.id.radioEmployee;
                    break;
                case "Other":
                    roleButtonId = R.id.radioOther;
                    break;
            }
            roleRadioGroup.check(roleButtonId);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

                if (name.isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(EditUserActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (selectedRoleId == -1) {
                    Toast.makeText(EditUserActivity.this, "Role is required", Toast.LENGTH_SHORT).show();
                } else {
                    String role = ((RadioButton) findViewById(selectedRoleId)).getText().toString();
                    user = new User(name, email, role);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("user", user);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}