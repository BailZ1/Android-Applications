package com.example.assignment01a;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final String TAG = "demo";

    // Assignment 1a
    // Assignment 1a
    // Bailey Bowling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        findViewById(R.id.CtoFbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Changing C to F");
                TextView tempInput = findViewById(R.id.enterTemp);
                String s = tempInput.getText().toString();

                if (s.isEmpty() || s.equals("Enter Temperature")) {
                    // Handle invalid input
                    ((TextView)findViewById(R.id.conversion)).setText("Invalid input");
                    return;
                }

                try {
                    int f = Integer.parseInt(s);
                    int c = (int) ((f - 32) * 0.555555556);
                    ((TextView)findViewById(R.id.conversion)).setText(String.valueOf(c) + " F");
                } catch (NumberFormatException e) {
                    // Handle invalid input
                    ((TextView)findViewById(R.id.conversion)).setText("Invalid input");
                }
            }
        });

        findViewById(R.id.FtoCbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Changing F to C");
                TextView tempInput = findViewById(R.id.enterTemp);
                String f = tempInput.getText().toString();

                if (f.isEmpty() || f.equals("Enter Temperature")) {
                    // Handle invalid input
                    ((TextView)findViewById(R.id.conversion)).setText("Invalid input");
                    return;
                }

                try {
                    int s = Integer.parseInt(f);
                    int c = (int) ((s * 1.8) + 32);
                    ((TextView)findViewById(R.id.conversion)).setText(String.valueOf(c) + " C");
                } catch (NumberFormatException e) {
                    // Handle invalid input
                    ((TextView)findViewById(R.id.conversion)).setText("Invalid input");
                }
            }
        });

        findViewById(R.id.ResetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Resetting");
                ((TextView)findViewById(R.id.enterTemp)).setText("");
                ((TextView)findViewById(R.id.conversion)).setText("    ");
            }
        });

    }
}