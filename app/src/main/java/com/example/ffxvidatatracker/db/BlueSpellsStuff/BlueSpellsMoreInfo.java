package com.example.ffxvidatatracker.db.BlueSpellsStuff;

import androidx.room.Entity;

@Entity
public class BlueSpellsMoreInfo {
    String type;
    String text;

    public BlueSpellsMoreInfo(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
