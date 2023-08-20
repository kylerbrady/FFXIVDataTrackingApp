package com.example.ffxvidatatracker;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ffxvidatatracker.db.Mounts.Achievements;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FindAchievements extends AsyncTask<String, Integer, String> {
    private String rawJSON;
    private String tempUrl;
    private OnAchievementsImport listener;

    public interface OnAchievementsImport{
        void completedAchievementList(List<Achievements> achievementsResults);
    }

    void setOnAchievementListImport(OnAchievementsImport listenerFromMain){
        listener = listenerFromMain;
    }
    void setCharacterId(String characterId){
        tempUrl = "https://ffxivcollect.com/api/characters/" + characterId + "/achievements/owned";
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
        List<Achievements> achievementsResults;
        try{
            achievementsResults = parseJson();
            listener.completedAchievementList(achievementsResults);
        }
        catch (Exception e){
            Log.d("GetCharacterData_Error", "onPostExecution " + e.toString());
        }

        super.onPostExecute(s);
    }

    private List<Achievements> parseJson(){

        Type listType = new TypeToken<List<Achievements>>() {}.getType();
        List<Achievements> AchievmentList = new Gson().fromJson(rawJSON, listType);
        return AchievmentList;
    }
}
