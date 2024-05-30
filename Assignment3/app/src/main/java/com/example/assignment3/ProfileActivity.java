package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_create);

        TextView nameTextView = findViewById(R.id.nameTextChange);
        TextView emailTextView = findViewById(R.id.emailTextChange);
        TextView roleTextView = findViewById(R.id.roleTextChange);
        Button updateButton = findViewById(R.id.updateButton);

        user = (User) getIntent().getSerializableExtra("user");

        if (user != null) {
            nameTextView.setText(user.getName());
            emailTextView.setText(user.getEmail());
            roleTextView.setText(user.getRole());
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditUserActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            user = (User) data.getSerializableExtra("user");
            // Update the UI with the new user details
            if (user != null) {
                ((TextView) findViewById(R.id.nameTextChange)).setText(user.getName());
                ((TextView) findViewById(R.id.emailTextChange)).setText(user.getEmail());
                ((TextView) findViewById(R.id.roleTextChange)).setText(user.getRole());
            }
        }
    }
}
