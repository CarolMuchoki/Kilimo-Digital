package com.example.carol.kilimodigital2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
    }

    public void gosell(View view) {
        Intent i=new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(i);
    }

    public void gobuy(View view) {
        Intent i=new Intent(getApplicationContext(),BuyActivity.class);
        startActivity(i);

    }
}
