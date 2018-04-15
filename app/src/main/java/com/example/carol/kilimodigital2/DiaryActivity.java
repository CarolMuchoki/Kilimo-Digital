package com.example.carol.kilimodigital2;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DiaryActivity extends AppCompatActivity {
    EditText Uname;
    EditText Uphone, txtDate;

    Button Submit;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener date;
    Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Uname = findViewById(R.id.name);
        Uphone = findViewById(R.id.phone);
        Submit = findViewById(R.id.submit);
        txtDate = findViewById(R.id.txtDate);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String formart = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(formart, Locale.US);
                txtDate.setText(sdf.format(mCalendar.getTime()));
            }
        };
        datePickerDialog=new DatePickerDialog(DiaryActivity.this,date,mCalendar
        .get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));


        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

    }

    public void userDiary(View view) {
        String name = Uname.getText().toString();
        String phone = Uphone.getText().toString();
        TaskBackground tsk = new TaskBackground(getApplicationContext());
        tsk.execute(name, phone);

        Intent intent = new Intent(getApplicationContext(), DisplayMessage.class);
        startActivity(intent);

    }


}

