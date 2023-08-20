package com.example.ffxvidatatracker.dbDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;

import java.util.ArrayList;

@Dao
public interface CharacterDAO {
        //Get all courses
        @Query("SELECT * FROM Character WHERE name =:name AND data_center=:data_center AND server=:server")
        Character getCharacterSearch(String name, String data_center, String server);

        @Query("SELECT * FROM Character WHERE id =:id")
        Character getById(int id);
        //List details of a course selected

        @Query("SELECT EXISTS(SELECT * FROM Character WHERE name =:name AND data_center=:data_center AND server=:server)")
        boolean doesExist(String name, String data_center, String server);

        @Update
        void update(Character course);
        //Update a course

        @Delete
        void delete(Character course);
        //Remove a course

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(ArrayList<Character> characters);//change this back if needed
        //ADD A COURSE

}
