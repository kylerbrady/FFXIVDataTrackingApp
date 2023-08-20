package com.example.ffxvidatatracker;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffxvidatatracker.db.AppDatabase;
import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;

import java.util.List;

public class AllBlueSepllsViewModel extends ViewModel {
    public LiveData<List<BlueSpells>> blueList;


    public  LiveData<List<BlueSpells>> getCourseList(Context c){
        if(blueList != null){
            return blueList;
        }
        else{
            return blueList = AppDatabase.getInstance(c).BlueSpellsDAO().getAll();
        }
    }
}
