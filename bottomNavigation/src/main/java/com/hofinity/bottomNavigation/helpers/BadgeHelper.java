package com.hofinity.bottomNavigation.helpers;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;

import com.hofinity.bottomNavigation.BottomNavigationView;
import com.hofinity.bottomNavigation.R;
import com.hofinity.bottomNavigation.models.BadgeItem;
import com.hofinity.bottomNavigation.utils.Utils;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public class BadgeHelper {

    /**
     * Force show badge without animation
     *
     * @param view      target budge
     * @param badgeItem BadgeItem object
     * @param show99plusSign Show full badge text or show 99+ for numbers greater than 99
     */
    public static void forceShowBadge(RelativeLayout view, BadgeItem badgeItem, boolean show99plusSign) {
        Utils.changeViewVisibility(view, View.VISIBLE);
        view.setBackground(createShapeDrawable(badgeItem.getBadgeColor(),1));
        TextView badgeTextView = view.findViewById(R.id.badgeTextView);
        if (show99plusSign) badgeTextView.setText(badgeItem.getBadgeText());
        else badgeTextView.setText(badgeItem.getFullBadgeText());
    }

    /**
     * Show badge
     *
     * @param view           target badge
     * @param badgeItem      BadgeItem object
     * @param badgeTextColor BadgeItem text color
     * @param show99plusSign Show full badge text or show 99+ for numbers greater than 99
     */
    public static void showBadge(RelativeLayout view, BadgeItem badgeItem, int badgeTextColor, boolean show99plusSign) {

        Utils.changeViewVisibility(view, View.VISIBLE);
        TextView badgeTextView = view.findViewById(R.id.badgeTextView);

        badgeTextView.setTextColor(badgeTextColor);

        if (show99plusSign) badgeTextView.setText(badgeItem.getBadgeText());
        else badgeTextView.setText(badgeItem.getFullBadgeText());

        view.setScaleX(0);
        view.setScaleY(0);

        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(1)
                .scaleY(1)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        Utils.changeViewVisibility(view, View.VISIBLE);
                    }
                })
                .start();
    }

    /**
     * Hide badge
     *
     * @param view target badge
     */
    public static void hideBadge(View view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(0)
                .scaleY(0)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final View view) {
                        Utils.changeViewVisibility(view, View.GONE);
                    }
                })
                .start();
    }

    /**
     * Creates shape drawable for badge background
     *
     * @param color          background color
     * @param badgeShapeType type of badge shape
     *
     * @return returns colored shape drawable
     */
    public static Drawable createShapeDrawable(int color, int badgeShapeType) {

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setStroke(3, color);

        if(badgeShapeType == BottomNavigationView.FILLED_OVAL ||
                badgeShapeType == BottomNavigationView.NOT_FILLED_OVAL) {
            if(badgeShapeType == BottomNavigationView.FILLED_OVAL) shape.setColor(color);
            shape.setCornerRadii(new float[] { 25, 25, 25, 25, 25, 25, 25, 25 });
        }

        if(badgeShapeType == BottomNavigationView.FILLED_RECTANGLE ||
                badgeShapeType == BottomNavigationView.NOT_FILLED_RECTANGLE) {
            if(badgeShapeType == BottomNavigationView.FILLED_RECTANGLE) shape.setColor(color);
            shape.setCornerRadii(new float[] { 8, 8, 8, 8, 8, 8, 8, 8 });
        }

//        ShapeDrawable badgeBackground = new ShapeDrawable(new OvalShape());
//        badgeBackground.setIntrinsicWidth(10);
//        badgeBackground.setIntrinsicHeight(10);
//        badgeBackground.getPaint().setColor(color);
        return shape;
    }
}