package com.example.carol.kilimodigital2;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Carol on 4/14/2018.
 */

public class Kilimo_Digital extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
