package com.example.assignment2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.res.ColorStateList;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SeekBar red, green, blue;

    ImageView imageView;

    int r, g, b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        red = findViewById(R.id.redSeekBar);
        green = findViewById(R.id.greenSeekBar);
        blue = findViewById(R.id.blueSeekBar);

        imageView.setImageTintList(ColorStateList.valueOf(Color.rgb(64, 128, 0)));
        red.setProgress(64); green.setProgress(128); blue.setProgress(0);

        updateColor();

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        //Set to White
        findViewById(R.id.whiteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setProgress(255); green.setProgress(255); blue.setProgress(255);
                updateColor();
            }
        });

        //Set to Black
        findViewById(R.id.blackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setProgress(0); green.setProgress(0); blue.setProgress(0);
                updateColor();
            }
        });

        //Set Blue
        findViewById(R.id.blueButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setProgress(0); green.setProgress(0); blue.setProgress(255);
                updateColor();
            }
        });


        //Reset Colors
        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setProgress(64); green.setProgress(128); blue.setProgress(0);
                updateColor();
            }
        });

    }

    private void updateColor() {
        r = red.getProgress();
        g = green.getProgress();
        b = blue.getProgress();

        ((TextView)findViewById(R.id.redValue)).setText(String.valueOf(r));
        ((TextView)findViewById(R.id.greenValue)).setText(String.valueOf(g));
        ((TextView)findViewById(R.id.blueValue)).setText(String.valueOf(b));

        imageView.setImageTintList(ColorStateList.valueOf(Color.rgb(r,g,b)));

        TextView textViewRGB = findViewById(R.id.rbgColor);
        TextView textViewHex = findViewById(R.id.hexColor);
        textViewRGB.setText(String.format("(%d, %d, %d)", r, g, b));
        textViewHex.setText(String.format("#%s", Integer.toHexString(Color.rgb(r, g, b)).toUpperCase()));
    }


}