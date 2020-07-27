package com.hofinity.bottomNavigation.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.hofinity.bottomNavigation.R;

import static com.hofinity.bottomNavigation.BottomNavigationView.LINEAR;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

@SuppressLint("ViewConstructor")
public class BezierView extends RelativeLayout {

    boolean showBorder = false;

    int bezierWidth;
    int bezierHeight;
    int borderThickness;
    int backgroundColor;
    int borderColor;
    int borderType;

    Context context;
    Paint paint;
    Path path;

    public BezierView(Context context, int backgroundColor, int borderColor) {
        super(context);
        this.context = context;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setBackgroundColor(ContextCompat.getColor(context, R.color.bm_transparent));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(borderType != LINEAR) {

            if(showBorder) {

                // Set paint color to fill border
                paint.setColor(borderColor);

                // Reset path before drawing
                path.reset();

                // Start point for drawing border
                path.moveTo(0, bezierHeight);

                // First path of bezier border
                path.cubicTo((float)bezierWidth / 4, bezierHeight, (float)bezierWidth / 4, 0, (float)bezierWidth / 2, 0);
                // Second part of bezier border
                path.cubicTo(((float)bezierWidth / 4) * 3, 0, ((float)bezierWidth / 4) * 3, bezierHeight, bezierWidth, bezierHeight);

                // Draw bezier border
                canvas.drawPath(path, paint);

            }

            // Set paint color to fill view
            paint.setColor(backgroundColor);

            // Reset path before drawing
            path.reset();

            // Start point for drawing
            path.moveTo(borderThickness, bezierHeight+borderThickness);

            // Seth half path of bezier view
            path.cubicTo((float)(bezierWidth+borderThickness) / 4, bezierHeight+borderThickness,
                    (float)(bezierWidth+borderThickness) / 4, borderThickness,
                    (float)(bezierWidth+borderThickness) / 2, borderThickness);
            // Seth second part of bezier view
            path.cubicTo(((float)(bezierWidth-borderThickness) / 4) * 3, borderThickness,
                    ((float)(bezierWidth-borderThickness) / 4) * 3, bezierHeight,
                    bezierWidth-borderThickness, bezierHeight+borderThickness);

            // Draw bezier view
            canvas.drawPath(path, paint);

        }
    }

    /**
     * Build bezier view with given width and height
     *
     * @param bezierWidth  Given width
     * @param bezierHeight Given height
     * @param borderThickness Border height
     * @param showBorder Border visibility
     * @param borderType True, if curves are not needed
     */
    public void build(int bezierWidth, int bezierHeight,int borderThickness,boolean showBorder,int borderType) {
        this.bezierWidth = bezierWidth;
        this.bezierHeight = bezierHeight;
        this.borderThickness = borderThickness;
        this.showBorder = showBorder;
        this.borderType = borderType;
    }

    /**
     * Change bezier view background color
     *
     * @param backgroundColor Target color
     */
    public void changeBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }
}