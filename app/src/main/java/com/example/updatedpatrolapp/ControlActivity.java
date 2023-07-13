package com.example.updatedpatrolapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.control_layout);

        Button button_UP = findViewById(R.id.buttonUp);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Up button"));

        Button button_Right = findViewById(R.id.buttonRight);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Right button"));

        Button button_Left = findViewById(R.id.buttonLeft);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Left button"));

        Button button_Down = findViewById(R.id.buttonDown);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Up button"));

        Button button_Brake = findViewById(R.id.buttonBreak);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Up button"));

        Button button_rotRight = findViewById(R.id.buttonRotRight);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Up button"));


        Button button_rotLeft = findViewById(R.id.buttonRotLeft);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Up button"));

    }
}
