package com.example.reciperader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    private TextView recName;
    private TextView calories;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        recName = findViewById(R.id.recName);
        calories =findViewById(R.id.calories);

        Intent intent = getIntent();

        String msg = intent.getStringExtra("nutrients");

        calories.setText(msg);



    }


    @Override
    protected void onStop() {
        super.onStop();
        calories.setText(calories.getText());

    }
}