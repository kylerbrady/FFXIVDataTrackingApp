package com.example.ffxvidatatracker;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CharacterLiveData extends ViewModel {
    private final MutableLiveData<CharSequence> characterId = new MutableLiveData<CharSequence>();
    public void inputedCharId(CharSequence charId){
        characterId.setValue(charId);
    }

    public LiveData<CharSequence> getCharId(){
        return characterId;
    }
}
