package com.example.ffxvidatatracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlueRecyclerViewAdapter extends RecyclerView.Adapter<BlueRecyclerViewAdapter.ViewHolder> {

    public final List<BlueSpells> blueSpells;

    public BlueRecyclerViewAdapter(List<BlueSpells> blueSpells) {
        this.blueSpells = blueSpells;
    }


    public void addItems(List<BlueSpells> blueList) {
        this.blueSpells.clear();
        this.blueSpells.addAll(blueList);
        notifyDataSetChanged();
        Log.e("In RecyclerViewAddItmes", String.valueOf(this.blueSpells.size()));
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public BlueSpells blueSpells;
        public TextView txtSpellName, txtSpellLocation;
        public ImageView imgIcon;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            view = itemView;
            imgIcon = (ImageView) view.findViewById(R.id.riIcon);
            txtSpellName = view.findViewById(R.id.riName);
            txtSpellLocation = view.findViewById(R.id.riLocation);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BlueSpells blueSpellItem = blueSpells.get(position);

        if(blueSpellItem != null){
            Picasso.get().load(blueSpellItem.getIcon()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.imgIcon, new Callback() {
                @Override
                public void onSuccess() {

                    String listOfLocations = "";//Used to get the entire list of spells
                    for(int i = 0; i < blueSpellItem.getSources().size(); i++){
                        listOfLocations = listOfLocations + blueSpellItem.getSources().get(i).getText() + "\n";
                    }

                    holder.txtSpellName.setText("#"+blueSpellItem.getOrder() +": "+ blueSpellItem.getName());
                    holder.txtSpellLocation.setText(listOfLocations);


                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("blue_pk", blueSpellItem.getId());
                            bundle.putString("blue_icon", blueSpellItem.getIcon());
                            BlueSpellDetails blueSpellDetails = new BlueSpellDetails();
                            blueSpellDetails.setArguments(bundle);

                            AppCompatActivity activity = (AppCompatActivity) v.getContext();

                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(android.R.id.content, blueSpellDetails)
                                    .addToBackStack(null)
                                    .commit();

                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("SpellImg_Error", e.toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return blueSpells.size();
    }


}