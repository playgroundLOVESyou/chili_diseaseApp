package com.example.chili_diseaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

Button start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);

            }
        }, 4000); // Delay for 4 second
    }

    }