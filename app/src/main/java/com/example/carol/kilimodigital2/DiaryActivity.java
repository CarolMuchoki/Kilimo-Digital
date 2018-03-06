package com.example.carol.kilimodigital2;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class DiaryActivity extends AppCompatActivity {
    EditText Uname;
    EditText Uphone;
    Button Submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Uname = (EditText) findViewById(R.id.name);
        Uphone = (EditText) findViewById(R.id.phone);
        Submit = (Button) findViewById(R.id.submit);

    }

    public void userDiary(View view) {
        String name = Uname.getText().toString();
        String phone = Uphone.getText().toString();
        TaskBackground tsk=new TaskBackground(getApplicationContext());
        tsk.execute(name, phone);

        Intent intent = new Intent(getApplicationContext(), DisplayMessage.class);
        startActivity(intent);

    }



    }

