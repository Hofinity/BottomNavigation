package com.hofinity.bottomNavigation.interfaces;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public interface BmOnClickListener {

    void onCenterButtonClick();
    void onItemClick(int index, String title);
    void onItemReselected(int index, String title);
}
