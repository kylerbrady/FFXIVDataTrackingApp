package com.example.ffxvidatatracker.db.BlueSpellsStuff;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

//Used to get the data for BlueSpells not placed in the DB
@Entity
public class BlueSpellsResults {
    @PrimaryKey(autoGenerate = true)
    int id;

    private int count;

    @TypeConverters(BlueSpellConverter.class)
    private List<BlueSpells> results;

    public BlueSpellsResults(int count, List<BlueSpells> results) {
        this.count = count;
        this.results = results;
    }

    public BlueSpellsResults(int tmpId, int tmpCount, List<BlueSpells> tmpResults) {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BlueSpells> getResults() {
        return results;
    }

    public void setResults(List<BlueSpells> results) {
        this.results = results;
    }
}
