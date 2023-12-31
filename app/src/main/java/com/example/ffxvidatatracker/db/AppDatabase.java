package com.example.ffxvidatatracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;
import com.example.ffxvidatatracker.dbDAO.BlueSpellsDAO;

@Database(entities = {BlueSpells.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if (instance !=null){
            return instance;
        }
        else  {
            instance = Room.databaseBuilder(context, AppDatabase.class, "user-database")
                    .build();
            return instance;
        }
    }

    public abstract BlueSpellsDAO BlueSpellsDAO();

}