package com.example.ffxvidatatracker;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ffxvidatatracker.db.CharacterStuff.Character;
import com.example.ffxvidatatracker.db.CharacterStuff.CharacterAchievements;
import com.example.ffxvidatatracker.db.Mounts.Mounts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FindMounts extends AsyncTask<String, Integer, String>{


    private String rawJSON;
    private String tempUrl;
    private OnMountImport listener;

    public interface OnMountImport{
        void completedMountList(List<Mounts> mountResults);
    }

    void setOnMountListImport(OnMountImport listenerFromMain){
        listener = listenerFromMain;
    }
    void setCharacterId(String characterId){
        tempUrl = "https://ffxivcollect.com/api/characters/" + characterId + "/mounts/owned";

    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            URL url = new URL(tempUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer "+Authorization.AUTH_TOKEN);
            connection.connect();

            int status = connection.getResponseCode();

            if (status == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                rawJSON = bufferedReader.readLine();

                Log.d("TestingConnection", "doInBackground: " + rawJSON.toString());
            }
        }
        catch (Exception e){
            Log.d("GetCharacterData_Error", "doInBackground" + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s){
        List<Mounts> mountsResults;
        try{
            mountsResults = parseJson();
            listener.completedMountList(mountsResults);
        }
        catch (Exception e){
            Log.d("GetCharacterData_Error", "onPostExecution " + e.toString());
        }

        super.onPostExecute(s);
    }

    private List<Mounts> parseJson(){

        Type listType = new TypeToken<List<Mounts>>() {}.getType();
        List<Mounts> mountsList = new Gson().fromJson(rawJSON, listType);
        return mountsList;
    }

}
