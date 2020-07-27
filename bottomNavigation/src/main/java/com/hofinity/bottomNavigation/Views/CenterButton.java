package com.hofinity.bottomNavigation.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public class CenterButton extends FloatingActionButton {

    public CenterButton(Context context) {
        super(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        boolean result = super.onTouchEvent(ev);
        if (!result) {
            if(ev.getAction() == MotionEvent.ACTION_UP) {
                cancelLongPress();
            }
            setPressed(false);
        }
        return result;
    }
}
