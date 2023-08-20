package com.example.ffxvidatatracker.db.Mounts;

import androidx.room.PrimaryKey;

public class Mounts {
    @PrimaryKey
    int id;
    String name;
    String description;
    String enhanced_description;
    String tooltip;
    int order;
    String icon;
    String bgm;

    String owned;

    public Mounts(int id,String name, String description, String enhanced_description, String tooltip, int order, String icon, String bgm, String owned) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enhanced_description = enhanced_description;
        this.tooltip = tooltip;
        this.order = order;
        this.icon = icon;
        this.bgm = bgm;
        this.owned = owned;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnhanced_description() {
        return enhanced_description;
    }

    public void setEnhanced_description(String enhanced_description) {
        this.enhanced_description = enhanced_description;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBgm() {
        return bgm;
    }

    public void setBgm(String bgm) {
        this.bgm = bgm;
    }

    public String getOwned() {
        return owned;
    }

    public void setOwned(String owned) {
        this.owned = owned;
    }
}
