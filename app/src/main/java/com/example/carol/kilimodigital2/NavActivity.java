package com.example.carol.kilimodigital2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class NavActivity extends AppCompatActivity {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Home_Model, NavActivity.NewsViewHolder> mPeopleRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Common Pests in Kenya");

        //"News" here will reflect what you have called your database in Firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("message");
       mDatabase.keepSynced(true);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);


        mPeopleRV = findViewById(R.id.recycler_view);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("message");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.setHasFixedSize(false);
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Home_Model>().setQuery(personsQuery, Home_Model.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Home_Model, NavActivity.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(NavActivity.NewsViewHolder holder, final int position, final Home_Model model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDescription());
                holder.setImage(getBaseContext(), model.getImage());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = model.getUrl();
                        Intent intent = new Intent(getApplicationContext(), Activity_Pest_Website.class);
                        intent.putExtra("id", url);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public NavActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_layout, parent, false);

                return new NavActivity.NewsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);
        mPeopleRV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy>0 && bottomNavigationView.isShown()){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if(dy<0){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
        /*CoordinatorLayout.LayoutParams layoutParams= (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
*/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home:

                        startActivity(new Intent(getApplicationContext(), NavActivity.class));

                        break;

                    case R.id.menu_skills:

                        startActivity(new Intent(getApplicationContext(), FarmSkills.class));

                        break;

                    case R.id.menu_diary:

                        startActivity(new Intent(getApplicationContext(), DiaryActivity.class));

                        break;

                    case R.id.menu_market:

                        startActivity(new Intent(getApplicationContext(), MarketActivity.class));

                        break;
                    case R.id.menu_transport:

                        startActivity(new Intent(getApplicationContext(), TransportActivity.class));

                        break;


                }


                return true;
            }
        });

        /*bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) BottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());*/

    }


    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();


    }



    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = mView.findViewById(R.id.topic);
            post_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView post_desc = mView.findViewById(R.id.content);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = mView.findViewById(R.id.image_pest);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}