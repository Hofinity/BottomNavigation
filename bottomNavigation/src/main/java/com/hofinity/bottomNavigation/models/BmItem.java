package com.hofinity.bottomNavigation.models;

import java.io.Serializable;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public class BmItem implements Serializable {

    int id = -1;
    int icon;
    String title;

    public BmItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public BmItem(int id, int icon) {
        this.id = id;
        this.icon = icon;
    }

    public BmItem(int id, String title, int icon) {
        this(title, icon);
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
