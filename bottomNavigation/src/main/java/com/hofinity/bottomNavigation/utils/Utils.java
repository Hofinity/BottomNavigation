package com.hofinity.bottomNavigation.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public class Utils {

    /**
     * Change view visibility
     *
     * @param view       target view
     * @param visibility visibility
     */
    public static void changeViewVisibility(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility)
            view.setVisibility(visibility);
    }

    /**
     * Change given image view tint
     *
     * @param imageView target image view
     * @param color     tint color
     */
    public static void changeImageViewTint(ImageView imageView, int color) {
        imageView.setColorFilter(color);
    }

    /**
     * Change given image view tint with animation
     *
     * @param image     target image view
     * @param fromColor start animation from color
     * @param toColor   final color
     */
    public static void changeImageViewTintWithAnimation(final ImageView image, int fromColor, int toColor) {
        ValueAnimator imageTintChangeAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        imageTintChangeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                image.setColorFilter((Integer) animator.getAnimatedValue());
            }
        });
        imageTintChangeAnimation.setDuration(150);
        imageTintChangeAnimation.start();
    }

    public static void makeTranslationYAnimation(View view, float value) {
        view.animate()
                .translationY(value)
                .setDuration(150)
                .start();
    }

    // TODO: add ripple effect programmatically
    @TargetApi(21)
    public static RippleDrawable getPressedColorRippleDrawable(int normalColor, int pressedColor) {
        return new RippleDrawable(getPressedColorSelector(normalColor, pressedColor), new ColorDrawable(normalColor), null);
    }

    public static ColorStateList getPressedColorSelector(int normalColor, int pressedColor) {
        return new ColorStateList(
                new int[][] {new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_activated}, new int[]{}},
                new int[] {pressedColor, pressedColor, pressedColor, normalColor}
        );
    }

    /**
     * Creates a new {@code RippleDrawable} used in Lollipop and later.
     *
     * @param normalColor Color for the idle ripple state
     * @param rippleColor Color for the clicked, pressed and focused ripple states
     * @param bounds Clip/mask drawable to these rectangle bounds
     *
     * @return A fully colored RippleDrawable instance
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable createRippleDrawable(int normalColor, int rippleColor, Rect bounds) {
        ColorDrawable maskDrawable = null;
        if (bounds != null) {
            maskDrawable = new ColorDrawable(Color.GREEN);
            maskDrawable.setBounds(bounds);
        }

        if (normalColor == Color.TRANSPARENT) {
            return new RippleDrawable(ColorStateList.valueOf(rippleColor), null, maskDrawable);
        } else {
            return new RippleDrawable(ColorStateList.valueOf(rippleColor), new ColorDrawable(normalColor), maskDrawable);
        }
    }
}
