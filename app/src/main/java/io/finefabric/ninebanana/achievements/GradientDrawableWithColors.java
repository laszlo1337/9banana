package io.finefabric.ninebanana.achievements;

import android.graphics.drawable.GradientDrawable;

/**
 * GradientDrawable that saves the drawable colors; used for AU background
 */
class GradientDrawableWithColors extends GradientDrawable {
    private int color;

    int getGradientColor() {
        return color;
    }

    @Override
    public void setColor(int argb) {
        super.setColors(new int[]{argb, argb});
        color = argb;
    }

    @Override
    public void setColors(int[] colors) {
        super.setColors(colors);
        color = colors[0];
    }
}
