package com.example.ffxvidatatracker.db.BlueSpellsStuff;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class BlueSpells {
        @PrimaryKey
        int id;

        String name;
        String description;
        String tooltip;
        int order;
        String icon;

        @TypeConverters(BlueSpellMoreInforConverter.class)
        List<BlueSpellsMoreInfo> sources;

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

        public List<BlueSpellsMoreInfo> getSources() {
                return sources;
        }

        public void setSources(List<BlueSpellsMoreInfo> sources) {
                this.sources = sources;
        }
}
