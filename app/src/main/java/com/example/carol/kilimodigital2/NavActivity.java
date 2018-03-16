package com.example.carol.kilimodigital2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

public class NavActivity extends AppCompatActivity {

    List<PestModel> pests;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        cardAdapter adapter=new cardAdapter(pests);
        rv.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:

                            case R.id.menu_skills:
                                Intent i=new Intent(getApplication(),FarmSkills.class);
                                startActivity(i);

                            case R.id.menu_diary:
                                Intent intent=new Intent(getApplication(),DiaryActivity.class);
                                startActivity(intent);




                        }
                        return true;
                    }
                });

    }
}
