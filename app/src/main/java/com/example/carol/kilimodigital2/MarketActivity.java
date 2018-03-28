package com.example.carol.kilimodigital2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MarketActivity extends AppCompatActivity {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Market_Model, MarketActivity.NewsViewHolder> mPeopleRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        setTitle("Kilimo Market Place");

        //"News" here will reflect what you have called your database in Firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("market");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) findViewById(R.id.rv);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("market");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Market_Model>().setQuery(personsQuery, Market_Model.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Market_Model, MarketActivity.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(MarketActivity.NewsViewHolder holder, final int position, final Market_Model model) {
                holder.setCrop_name(model.getCrop_name());
                holder.setPrice(model.getPrice());
                holder.setImage(getBaseContext(), model.getImage());

            }

            @Override
            public MarketActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.market_card_layout, parent, false);

                return new NewsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);


    }



    public void gosell(View view) {
        Intent i=new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(i);
    }

    public void gobuy(View view) {
        Intent i=new Intent(getApplicationContext(),BuyActivity.class);
        startActivity(i);

    }




    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setCrop_name(String crop_name) {
            TextView post_crop_name = (TextView) mView.findViewById(R.id.crop_name);
            post_crop_name.setText(crop_name);
        }

        public void setPrice(String price) {
            TextView post_price = (TextView) mView.findViewById(R.id.price);
            post_price.setText(price);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.crop);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }
}
