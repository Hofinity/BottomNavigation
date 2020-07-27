package com.hofinity.bottomNavigation.models;

import java.io.Serializable;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public class BadgeItem implements Serializable {

    static final int BADGE_TEXT_MAX_NUMBER = 99;
    int badgeIndex;
    int badgeText;
    int badgeColor;

    public BadgeItem(int badgeIndex, int badgeText, int badgeColor) {
        this.badgeIndex = badgeIndex;
        this.badgeText = badgeText;
        this.badgeColor = badgeColor;
    }

    public int getBadgeIndex() {
        return badgeIndex;
    }

    public int getBadgeColor() {
        return badgeColor;
    }

    public int getIntBadgeText() {
        return badgeText;
    }

    public String getFullBadgeText() {
        return String.valueOf(badgeText);
    }

    public String getBadgeText() {
        String badgeStringText;
        if (badgeText > BADGE_TEXT_MAX_NUMBER) {
            badgeStringText = BADGE_TEXT_MAX_NUMBER + "+";
        } else {
            badgeStringText = "" + badgeText;
        }

        return badgeStringText;
    }
}
