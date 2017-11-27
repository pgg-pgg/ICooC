package com.pgg.icookapp.Utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by PDD on 2017/7/14.
 */
public class DrawableUtils {
    public static Drawable getGradientDrawable(int rgb, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setColor(rgb);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static Drawable getStateListDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[] { android.R.attr.state_pressed }, pressed);
        drawable.addState(new int[] {}, normal);
        return drawable;
    }


    public static Drawable getStateListDrawable(int normalColor, int pressedColor, int radius) {
        Drawable normal = getGradientDrawable(normalColor, radius);
        Drawable pressed = getGradientDrawable(pressedColor, radius);
        return getStateListDrawable(normal, pressed);
    }
}
