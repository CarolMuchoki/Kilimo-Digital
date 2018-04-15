package com.example.carol.kilimodigital2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

    private RecyclerView mRecycle;
    private DatabaseReference mDatabase;

    private FloatingActionButton fab;
    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager mLayoutManager;
    TextView loading;
    DatabaseReference mDatabaseReference;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        mRecyclerView = findViewById(R.id.items_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarketActivity.this,Sell.class));
            }
        });
        loading = findViewById(R.id.loading);


        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);


    }




    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<ItemData>().setQuery(mDatabaseReference, ItemData.class).build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemData, ItemViewHolder>(personsOptions) {
            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ItemData model) {
                holder.setTitle(model.getTitle());
                holder.setPrice(model.getPrice());
                holder.setImage(MarketActivity.this, model.getImage());
            }

        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

            

            /*Override
            protected void populateViewHolder(ItemViewHolder viewHolder, final ItemData model, int position) {
                loading.setVisibility(View.GONE);
                final String item_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(MarketActivity.this, model.getImage());
                *//*viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //vRef = FirebaseDatabase.getInstance().getReference().child("Items").child(item_key);
                        //final Map<String, Object> map = new HashMap<>();
                        // String views=String.valueOf(model.getViews());
                        *//**//*int views1=Integer.parseInt(views);
                        map.put("views", views1+1);
                        vRef.updateChildren(map);*//**//*

                       *//**//* Intent adDetails = new Intent(v.getContext(), AdDetails.class);
                        adDetails.putExtra("item_id", item_key);
                        startActivity(adDetails);*//**//*
                    }
                });*//*
            }
        };*/



    public static class ItemViewHolder extends RecyclerView.ViewHolder {


        View mView;

        public ItemViewHolder(View v) {
            super(v);
            mView = v;

        }

        public void setTitle(String title) {
            TextView tvTitle = mView.findViewById(R.id.tv_title);
            tvTitle.setText(title);
        }

        public void setPrice(String price) {

            TextView txtPrice = mView.findViewById(R.id.tv_price);
            txtPrice.setText("Ksh. " + price);

        }

        public void setImage(Context context, String image) {
            ImageView ivImage = mView.findViewById(R.id.iv_image);
            if (image != null) {
                Picasso.with(context).load(image).resize(200, 200).into(ivImage);
            } else {
                Picasso.with(context).load(R.drawable.noimage).resize(200, 200).into(ivImage);

            }
        }


    }







  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        setTitle("Kilimo Market Place");

        //"News" here will reflect what you have called your database in Firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("market");
        mDatabase.keepSynced(true);

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarketActivity.this,Sell.class));
            }
        });
        mRecycle = findViewById(R.id.rv);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("market");
        Query personsQuery = personsRef.orderByKey();

        mRecycle.setHasFixedSize(false);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Market_Model>().setQuery(personsQuery, Market_Model.class).build();

        mRecycleAdapter = new FirebaseRecyclerAdapter<Market_Model, MarketActivity.NewsViewHolder>(personsOptions) {
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

        mRecycle.setAdapter(mRecycleAdapter);


    }


    public void gosell(View view) {
        Intent i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
    }

    public void gobuy(View view) {
        Intent i = new Intent(getApplicationContext(), BuyActivity.class);
        startActivity(i);

    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setCrop_name(String crop_name) {
            TextView post_crop_name = mView.findViewById(R.id.crop_name);
            post_crop_name.setText(crop_name);
        }

        public void setPrice(String price) {
            TextView post_price = mView.findViewById(R.id.price);
            post_price.setText(price);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = mView.findViewById(R.id.crop);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }*/
}
