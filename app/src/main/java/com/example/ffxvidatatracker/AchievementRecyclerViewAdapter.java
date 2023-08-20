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

import com.example.ffxvidatatracker.db.Mounts.Achievements;
import com.example.ffxvidatatracker.db.Mounts.Mounts;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AchievementRecyclerViewAdapter extends RecyclerView.Adapter<AchievementRecyclerViewAdapter.ViewHolder>{
    public final List<Achievements> achievementsList;

    public AchievementRecyclerViewAdapter(List<Achievements> achievementsList) {this.achievementsList = achievementsList; }

    public void addItems(List<Achievements> achievementsLists) {
        this.achievementsList.clear();
        this.achievementsList.addAll(achievementsLists);
        notifyDataSetChanged();
        Log.e("In RecyclerViewAddItmes", String.valueOf(this.achievementsList.size()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public Achievements characterAchievements;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mount_achievement_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Achievements achievementItem = achievementsList.get(position);

        if(achievementItem != null){
            Picasso.get().load(achievementItem.getIcon()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.imgIcon, new Callback() {
                @Override
                public void onSuccess() {
                    holder.txtName.setText(achievementItem.getName());
                    holder.txtOwnedPercent.setText("Owned: " + achievementItem.getOwned());
                    holder.txtDescription.setText(achievementItem.getDescription());

                    holder.view.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Bundle bundle = new Bundle();
                            bundle.putString("achievement_icon", achievementItem.getIcon());
                            bundle.putString("achievement_name", achievementItem.getName());
                            bundle.putString("achievement_desc", achievementItem.getDescription());
                            bundle.putString("achievement_owned", achievementItem.getOwned());
                            AchievementDetails achievementDetails = new AchievementDetails();
                            achievementDetails.setArguments(bundle);

                            AppCompatActivity activity = (AppCompatActivity) v.getContext();

                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(android.R.id.content, achievementDetails)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("AchievementImg_Error", e.toString());

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return achievementsList.size();
    }
}
