package com.example.carol.kilimodigital2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TransportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
    }

    public void goListofDrivers(View view) {

        startActivity(new Intent(getApplicationContext(), ListofDriversActivity.class));

    }
}
