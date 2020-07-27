package com.hofinity.bottomNavigation.behaviors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.Snackbar;
import com.hofinity.bottomNavigation.utils.Utils;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public class BmBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public BmBehavior(Context context, AttributeSet attrs) {
        super();
    }

    public BmBehavior() {
        super();
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, final V child, View dependency) {

        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                                       @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes) {

        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout, @NonNull final V child,
                               @NonNull final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0) Utils.makeTranslationYAnimation(child, child.getHeight());
        else if (dyConsumed < 0) Utils.makeTranslationYAnimation(child, 0);
    }
}
