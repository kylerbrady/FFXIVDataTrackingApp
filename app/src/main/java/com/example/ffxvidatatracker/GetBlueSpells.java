package com.example.ffxvidatatracker;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpellsResults;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetBlueSpells extends AsyncTask<String, Integer, String> {
    private String rawJSON;

    private OnBlueSpellListImport listener;

    public interface OnBlueSpellListImport{
        void completedBlueSpellList(BlueSpellsResults blueSpellsResults);
    }

    void setOnBlueSpellListImportListener(OnBlueSpellListImport listenerFromMain){
        listener = listenerFromMain;
    }
    @Override
    protected String doInBackground(String... strings) {
        try{
            URL url = new URL("https://ffxivcollect.com/api/spells");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer "+Authorization.AUTH_TOKEN);
            connection.connect();

            int status = connection.getResponseCode();

            if (status == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                rawJSON = bufferedReader.readLine();

            }
        }
        catch (Exception e){
            Log.d("GetBlueSpellData_Error", "doInBackground" + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s){
        BlueSpellsResults blueSpellsResults;
        try{
            blueSpellsResults = parseJson();
            listener.completedBlueSpellList(blueSpellsResults);
        }
        catch (Exception e){
            Log.d("GetBlueSpellData_Error", "onPostExecution " + e.toString());
        }

        super.onPostExecute(s);
    }

    private BlueSpellsResults parseJson(){


        BlueSpellsResults blueSpellsResults = new Gson().fromJson(rawJSON, BlueSpellsResults.class);
        return blueSpellsResults;
    }
}
