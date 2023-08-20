package com.example.ffxvidatatracker.db.Mounts;

import androidx.room.PrimaryKey;

public class Achievements {
        @PrimaryKey
        int id;
        String name;
        String description;
        int order;
        String icon;

        String owned;

        public Achievements(int id,String name, String description, int order, String icon, String owned) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.order = order;
            this.icon = icon;
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

        public String getOwned() {
            return owned;
        }

        public void setOwned(String owned) {
            this.owned = owned;
        }

}
