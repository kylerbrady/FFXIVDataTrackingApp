package com.example.ffxvidatatracker;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpellsResults;
import com.example.ffxvidatatracker.db.CharacterStuff.Character;
import com.example.ffxvidatatracker.db.CharacterStuff.CharacterMounts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FindCharacter extends AsyncTask<String, Integer, String> {
    private String rawJSON;
    private String tempUrl;
    private String userId;//We need to pass this in on a text view to take in an id value to pull up character data
    private OnCharacterListImport listener;

    public interface OnCharacterListImport{
        void completedCharacter(Character characterResults);
    }

    void setOnCharacterListListener(OnCharacterListImport listenerFromMain){
        listener = listenerFromMain;
    }
    void setCharacterId(String characterId){
        tempUrl = "https://ffxivcollect.com/api/characters/" + characterId;

    }

    @Override
    protected  String doInBackground(String... strings) {
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
        Character CharacterResults;
        try{
            CharacterResults = parseJson();
            listener.completedCharacter(CharacterResults);
        }
        catch (Exception e){
            Log.d("GetCharacterData_Error", "onPostExecution " + e.toString());
        }

        super.onPostExecute(s);
    }

    private Character parseJson(){


        Character CharacterResults = new Gson().fromJson(rawJSON, Character.class);
        return CharacterResults;
    }
}
