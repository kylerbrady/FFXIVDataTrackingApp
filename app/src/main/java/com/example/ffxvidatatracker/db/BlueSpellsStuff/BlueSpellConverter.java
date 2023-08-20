package com.example.ffxvidatatracker.db.BlueSpellsStuff;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class BlueSpellConverter {


    @TypeConverter
    public static List<BlueSpells> storedBlueSpellsString(String data){
        Gson gson = new Gson();
        if(data==null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<BlueSpells>>(){}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String storeBlueSpellsString(List<BlueSpells> blueSpellsList){
        Gson gson = new Gson();
        return gson.toJson(blueSpellsList);
    }

}
