package com.example.carol.kilimodigital2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisplayMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
    }

    public void goHome(View view) {
        Intent i= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    public void godiary(View view){

        Intent i= new Intent(getApplicationContext(),DiaryActivity.class);
        startActivity(i);
    }

    }

