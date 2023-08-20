package com.example.ffxvidatatracker.db.CharacterStuff;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class Character {
    @PrimaryKey
    int id;

    String name;

    String server;

    String data_center;

    String portrait;

    String avatar;

    CharacterMounts mounts;
    CharacterAchievements achievements;

    public Character(int id, String name, String server, String data_center, String portrait, String avatar, CharacterMounts mounts, CharacterAchievements achievements) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.data_center = data_center;
        this.portrait = portrait;
        this.avatar = avatar;
        this.mounts = mounts;
        this.achievements = achievements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getData_center() {
        return data_center;
    }

    public void setData_center(String data_center) {
        this.data_center = data_center;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public CharacterMounts getMounts() {
        return mounts;
    }

    public void setMounts(CharacterMounts mounts) {
        this.mounts = mounts;
    }

    public CharacterAchievements getAchievements() {
        return achievements;
    }

    public void setAchievements(CharacterAchievements achievements) {
        this.achievements = achievements;
    }

    public Character(){}
}
