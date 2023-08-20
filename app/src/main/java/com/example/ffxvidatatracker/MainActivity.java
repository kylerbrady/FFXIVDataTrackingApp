package com.example.ffxvidatatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ffxvidatatracker.db.AppDatabase;
import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpells;
import com.example.ffxvidatatracker.db.BlueSpellsStuff.BlueSpellsResults;
import com.example.ffxvidatatracker.db.CharacterStuff.Character;
import com.example.ffxvidatatracker.db.CharacterStuff.CharacterMounts;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GetBlueSpells getBlueSpells;
    private FindCharacter findCharacter;
    FragmentManager fm;
    boolean nightMode;
    public String CharacterId = "";
    Fragment selectedFragment = null;

    public void setCharacterId(String charId){
        this.CharacterId = charId;
    };
    public String getCharacterId(){return CharacterId;}

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);
        if(nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment())
                .commit();
        LoadBlueSpells();

        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        NavigationBarView.OnItemSelectedListener navListener =
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item){
                        if( item.getItemId() == R.id.nav_home ){
                            selectedFragment = new HomeFragment();
                        } else if (item.getItemId() == R.id.nav_blue) {
                            selectedFragment = new BlueSpellFragment();
                        } else if (item.getItemId() == R.id.nav_mounts) {
                            selectedFragment = new MountFragment();
                        }else if(item.getItemId()==R.id.nav_achievements){
                            selectedFragment = new AchievementFragment();
                        }else {
                            throw new ClassCastException("Must implement:" + item);
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                        return true;
                    }
                };
        bottomNavigationView.setOnItemSelectedListener(navListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuResetLogs) {
            CharacterId = "";
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,  new HomeFragment())
                    .commit();
            return true;
        } else if (itemId == R.id.menuChangeTheme) {

            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = sharedPreferences.edit();
                editor.putBoolean("night", false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = sharedPreferences.edit();
                editor.putBoolean("night", true);
            }
            editor.apply();
            if (selectedFragment != null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





    private void LoadBlueSpells() {
        getBlueSpells = new GetBlueSpells();
        getBlueSpells.setOnBlueSpellListImportListener(new GetBlueSpells.OnBlueSpellListImport() {
            @Override
            public void completedBlueSpellList(BlueSpellsResults blueSpellsResults) {
                final ArrayList<BlueSpells> resultsList = new ArrayList<>();

                for(BlueSpells c: blueSpellsResults.getResults()){
                    resultsList.add(c);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BlueSpells blueSpells;
                        Log.d("", "run: ");

                        //If data does not exist fill database
                        try {
                            AppDatabase.getInstance(getApplicationContext())
                                    .BlueSpellsDAO()
                                    .insert(resultsList);
                        }
                        catch (Exception e){
                            Log.d("BlueDB", "Database already exists");
                        }
                    }
                }).start();
            }
        });
        getBlueSpells.execute("");
    }
}
