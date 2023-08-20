package com.example.ffxvidatatracker;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class BlueSpellFragment extends Fragment {

    private RecyclerView recyclerView;

    BlueRecyclerViewAdapter blueRecyclerViewAdapter;
    private int columnCount = 1;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.blue_spell_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Context context = getContext();
        blueRecyclerViewAdapter = new BlueRecyclerViewAdapter(new ArrayList<BlueSpells>());

        if(columnCount <= 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }

        recyclerView.setAdapter(blueRecyclerViewAdapter);
        recyclerView.setHasFixedSize(false);

        ViewModelProviders.of(this)
                .get(AllBlueSepllsViewModel.class)
                .getCourseList(context)
                .observe(this, new Observer<List<BlueSpells>>() {
                    @Override
                    public void onChanged(List<BlueSpells> blueSpellsList) {
                        if (blueSpellsList != null){
                            blueRecyclerViewAdapter.addItems(blueSpellsList);
                        }
                    }
                });
    }
}