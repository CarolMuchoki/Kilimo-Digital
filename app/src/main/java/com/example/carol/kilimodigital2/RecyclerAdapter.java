package com.example.carol.kilimodigital2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon.Wambua on 24/03/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Home_Model> products;


    public RecyclerAdapter(Context context, List<Home_Model> products) {
        this.products = new ArrayList<>(products);
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;
        public ImageView image;
        private CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.topic);
            content = (TextView) view.findViewById(R.id.content);
            image = (ImageView) view.findViewById(R.id.image);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            itemView.setClickable(true);
            itemView.setFocusableInTouchMode(true);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "inside viewholder position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.MyViewHolder holder, final int position) {
        final Home_Model product = products.get(position);

        //  L.T(mContext, "The id is" + notification);

        holder.title.setText(product.getTitle());
        holder.content.setText(product.getDescription());


        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

              /*  Intent intent = new Intent(view.getContext(), Activity_CardInfo.class);


                intent.putExtra("product_id", product.getProduct_id());
                // L.T(mContext, "The id is" + product.getNotification_id());
                intent.putExtra("topic", holder.name.getText().toString());
                intent.putExtra("content", holder.description.getText().toString());
                intent.putExtra("company", holder.provider.getText().toString());
                intent.putExtra("category", holder.category.getText().toString());
                intent.putExtra("image", product.getImage());

                ((Activity) mContext).startActivityForResult(intent, 1);*/
            }
        });


       /* text drawable*/
    /*    String firstLetter = products.get(position).getName().charAt(0) + "";
        ColorGenerator generator = ColorGenerator.MATERIAL;
*/
      /*  int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);
        holder.image.setImageDrawable(drawable);*/

       /* load image with glide*/
        Glide.with(mContext).load(product.getImage()).placeholder(R.drawable.logo).crossFade().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
