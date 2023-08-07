package com.example.updatedpatrolapp;

import android.content.Intent;
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

        Button button_UP = findViewById(R.id.Up_button);
        button_UP.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Up button"));

        Button button_Right = findViewById(R.id.buttonRight);
        button_Right.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Right button"));

        Button button_Left = findViewById(R.id.buttonLeft);
        button_Left.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Left button"));

        Button button_Down = findViewById(R.id.buttonDown);
        button_Down.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Down button"));

        Button button_Brake = findViewById(R.id.buttonBreak);
        button_Brake.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Break button"));

        Button button_rotRight = findViewById(R.id.buttonRotRight);
        button_rotRight.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Rotate Right button"));


        Button button_rotLeft = findViewById(R.id.buttonRotLeft);
        button_rotLeft.setOnClickListener(v -> Log.d("BUTTONS", "User tapped the Rotate Left button"));

        Button Switch_Back = findViewById(R.id.Switch_Back);
        Switch_Back.setOnClickListener(view -> {
            Intent intent = new Intent(ControlActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}
