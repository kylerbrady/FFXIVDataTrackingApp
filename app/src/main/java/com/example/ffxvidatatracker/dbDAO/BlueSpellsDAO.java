package com.example.ffxvidatatracker.dbDAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BlueSpellsDAO {
    //Get all courses
    @Query("SELECT * FROM BlueSpells ORDER BY BlueSpells.`order`")
    LiveData<List<BlueSpells>> getAll();
    @Query("SELECT * FROM BlueSpells WHERE id =:id")
    BlueSpells getById(int id);
    //List details of a course selected

    @Query("SELECT EXISTS(SELECT * FROM BlueSpells WHERE id =:id)")
    boolean doesExist(int id);

    @Update
    void update(BlueSpells course);
    //Update a course

    @Delete
    void delete(BlueSpells course);
    //Remove a course

    @Insert
    void insert(ArrayList<BlueSpells> blueSpells);//change this back if needed
    //ADD A COURSE
}