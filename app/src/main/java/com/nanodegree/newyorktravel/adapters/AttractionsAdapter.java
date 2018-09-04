package com.nanodegree.newyorktravel.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Attraction;

import java.util.ArrayList;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.AttractionViewHolder>{

    private ArrayList<Attraction> attractions;

    public AttractionsAdapter(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttractionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attraction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        Attraction attraction = attractions.get(position);

        holder.itemName.setText(attraction.getAttractionName());
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    class AttractionViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;

        public AttractionViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_attraction_name);
        }
    }
}