package com.example.ffxvidatatracker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;
import com.example.ffxvidatatracker.db.CharacterStuff.CharacterMounts;
import com.example.ffxvidatatracker.db.Mounts.Mounts;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MountRecyclerViewAdapter extends RecyclerView.Adapter<MountRecyclerViewAdapter.ViewHolder> {
    public final List<Mounts> mountsList;

    public MountRecyclerViewAdapter(List<Mounts> mountsList) {this.mountsList = mountsList; }


    public void addItems(List<Mounts> mountsList) {
        this.mountsList.clear();
        this.mountsList.addAll(mountsList);
        notifyDataSetChanged();
        Log.e("In RecyclerViewAddItems", String.valueOf(this.mountsList.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mount_achievement_recycler_item, parent, false);
        return new ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public Mounts characterMounts;
        public TextView txtName, txtOwnedPercent, txtDescription;
        public ImageView imgIcon;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            view = itemView;
            imgIcon = (ImageView) view.findViewById(R.id.riIcon);
            txtName = view.findViewById(R.id.riName);
            txtOwnedPercent = view.findViewById(R.id.riOwnedPercent);
            txtDescription = view.findViewById(R.id.riDescription);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Mounts mountItem = mountsList.get(position);

        if(mountItem != null){
            Picasso.get().load(mountItem.getIcon()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.imgIcon, new Callback() {
                @Override
                public void onSuccess() {
                    holder.txtName.setText(mountItem.getName());
                    holder.txtOwnedPercent.setText("Owned: " + mountItem.getOwned());
                    holder.txtDescription.setText(mountItem.getDescription());

                    holder.view.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Bundle bundle = new Bundle();
                            bundle.putString("mount_icon", mountItem.getIcon());
                            bundle.putString("mount_name", mountItem.getName());
                            bundle.putString("mount_desc", mountItem.getDescription());
                            bundle.putString("mount_journal", mountItem.getEnhanced_description());
                            bundle.putString("mount_owned", mountItem.getOwned());
                            bundle.putString("mount_tooltip", mountItem.getTooltip());
                            bundle.putString("mount_bgm", mountItem.getBgm());
                            MountDetails mountDetails = new MountDetails();
                            mountDetails.setArguments(bundle);

                            AppCompatActivity activity = (AppCompatActivity) v.getContext();

                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(android.R.id.content, mountDetails)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("MountImg_Error", e.toString());

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mountsList.size();
    }





}
