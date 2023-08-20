package com.example.ffxvidatatracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffxvidatatracker.db.Mounts.Achievements;
import com.example.ffxvidatatracker.db.Mounts.Mounts;

import java.util.ArrayList;
import java.util.List;

public class AchievementFragment extends Fragment {

    private RecyclerView recyclerView;
    FindAchievements findAchievements;
    AchievementRecyclerViewAdapter achievementRecyclerViewAdapter;
    private int columnCount = 1;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_achievement, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
   }


    @Override
    public void onResume(){
        super.onResume();
        Context context = getContext();
        achievementRecyclerViewAdapter = new AchievementRecyclerViewAdapter(new ArrayList<Achievements>());

        if(columnCount <= 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
        FindAchievements();
        recyclerView.setAdapter(achievementRecyclerViewAdapter);
        recyclerView.setHasFixedSize(false);

    }

    private boolean FindAchievements(){
        findAchievements = new FindAchievements();
        String charID = ((MainActivity) getContext()).getCharacterId();
        findAchievements.setCharacterId(charID);

        try {

            findAchievements.setOnAchievementListImport(new FindAchievements.OnAchievementsImport() {
                @Override
                public void completedAchievementList(List<Achievements> achievementsResults) {
                    ArrayList<Achievements> results = new ArrayList<>();
                    achievementRecyclerViewAdapter.addItems(achievementsResults);
                }
            });
            findAchievements.execute("");
            return true;
        }
        catch (Error e) {
            Log.e("FindCharacter_error", e.toString());
        }

        return false;
    }

}