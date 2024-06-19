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

public class CreateUserActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private RadioGroup roleRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nameEditText = findViewById(R.id.enterName);
        emailEditText = findViewById(R.id.enterEmail);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

                if (name.isEmpty()) {
                    Toast.makeText(CreateUserActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(CreateUserActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(CreateUserActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (selectedRoleId == -1) {
                    Toast.makeText(CreateUserActivity.this, "Role is required", Toast.LENGTH_SHORT).show();
                } else {
                    String role = ((RadioButton) findViewById(selectedRoleId)).getText().toString();
                    User user = new User(name, email, role);
                    Intent intent = new Intent(CreateUserActivity.this, ProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
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