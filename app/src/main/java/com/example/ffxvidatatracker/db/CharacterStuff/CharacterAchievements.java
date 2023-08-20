package com.example.ffxvidatatracker.db.CharacterStuff;

import androidx.room.Entity;

@Entity
public class CharacterAchievements {
    int count;
    int total;

    public CharacterAchievements(int count, int total) {
        this.count = count;
        this.total = total;
    }
    public CharacterAchievements(){}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
