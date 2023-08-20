package com.example.ffxvidatatracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;
import com.example.ffxvidatatracker.db.CharacterStuff.Character;
import com.example.ffxvidatatracker.db.Mounts.Mounts;

import java.util.ArrayList;
import java.util.List;


public class MountFragment extends Fragment {
    private RecyclerView recyclerView;
    FindMounts findMounts;
    MountRecyclerViewAdapter mountRecyclerViewAdapter;
    private int columnCount = 1;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.mount_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Context context = getContext();
        mountRecyclerViewAdapter = new MountRecyclerViewAdapter(new ArrayList<Mounts>());

        if(columnCount <= 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
        FindMount();
        recyclerView.setAdapter(mountRecyclerViewAdapter);
        recyclerView.setHasFixedSize(false);

    }

    private boolean FindMount(){
        findMounts = new FindMounts();
        try {
            String charId = ((MainActivity) getContext()).getCharacterId();
            findMounts.setCharacterId(charId);
            findMounts.setOnMountListImport(new FindMounts.OnMountImport() {
                @Override
                public void completedMountList(List<Mounts> mountResults) {
                    ArrayList<Mounts> results = new ArrayList<>();
                    mountRecyclerViewAdapter.addItems(mountResults);
                }
            });
            findMounts.execute("");
            return true;
        }
        catch (Error e) {
            Log.e("FindCharacter_error", e.toString());
        }

        return false;
    }
}