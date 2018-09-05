package com.nanodegree.newyorktravel.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Attraction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.AttractionViewHolder> {

    public interface AttractionListener {
        void onAttractionClicked(Attraction attraction);
    }

    private ArrayList<Attraction> attractions;
    private AttractionListener listener;

    public AttractionsAdapter(ArrayList<Attraction> attractions, AttractionListener attractionListener) {
        this.attractions = attractions;
        this.listener = attractionListener;
    }

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttractionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attraction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        final Attraction attraction = attractions.get(position);

        holder.itemName.setText(attraction.getName());
        holder.itemDesc.setText(attraction.getDescription());

        if(!TextUtils.isEmpty(attraction.getImageUrl())) {
            Picasso.get().load(attraction.getImageUrl()).resize(300,300).into(holder.imgView);
            holder.imgView.setContentDescription(attraction.getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAttractionClicked(attraction);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    class AttractionViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemDesc;
        ImageView imgView;

        public AttractionViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_attraction_name);
            itemDesc = itemView.findViewById(R.id.item_attraction_desc);
            imgView = itemView.findViewById(R.id.item_attraction_img);
        }
    }
}