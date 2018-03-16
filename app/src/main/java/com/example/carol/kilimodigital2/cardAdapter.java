package com.example.carol.kilimodigital2;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carol on 3/15/2018.
 */

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.PestModelViewHolder> {
    public class PestModelViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        TextView pestName;
        TextView readMore;
        ImageView pestphoto;
        Button showMore;

        public PestModelViewHolder(View itemView) {
            super(itemView);
            card_view=(CardView)itemView.findViewById(R.id.card_view);
            pestName=(TextView)itemView.findViewById(R.id.pest_name);
            readMore=(TextView)itemView.findViewById(R.id.readMore);
            pestphoto=(ImageView)itemView.findViewById(R.id.pest_photo);
            showMore=(Button)itemView.findViewById(R.id.showmore);

        }
    }
    List<PestModel> pests;

    cardAdapter(List<PestModel> pests){
        this.pests = pests;
    }
    @Override
    public PestModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card,viewGroup,false);
        PestModelViewHolder pvh=new PestModelViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PestModelViewHolder pestModelViewHolder, int i) {
        pestModelViewHolder.pestName.setText(pests.get(i).pestName);
        pestModelViewHolder.readMore.setText(pests.get(i).readMore);
        pestModelViewHolder.pestphoto.setImageResource(pests.get(i).photoId);

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return pests.size();
    }


}
