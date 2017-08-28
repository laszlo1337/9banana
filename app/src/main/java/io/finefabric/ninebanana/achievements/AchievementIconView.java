package io.finefabric.ninebanana.achievements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * ImageView that animates drawable change. It also scales
 * according to the drawable size to make sure it doesn't clip
 */
@SuppressLint("AppCompatCustomView")
public class AchievementIconView extends ImageView {
    public AchievementIconView(Context context) {
        super(context);
    }

    public enum AchievementIconViewStates {
        FADE_DRAWABLE, SAME_DRAWABLE
    }


    public void setDrawable(final Drawable drawable) {
        if (drawable == null) {
            setImageDrawable(null);
            return;
        }
        if (getScaleType() != ScaleType.CENTER_CROP) setScaleType(ScaleType.CENTER_CROP);

        final float scaleX = 3.5f / (getMaxWidth() / drawable.getIntrinsicWidth());
        final float scaleY = 3.5f / (getMaxWidth() / drawable.getIntrinsicHeight());

        if (getDrawable() == null) {
            setImageDrawable(drawable);
            setScaleX(1 / Math.max(scaleX, scaleY));
            setScaleY(1 / Math.max(scaleX, scaleY));
        } else {
            if (drawable.getAlpha() < 255)
                drawable.setAlpha(255);

            animate().scaleX(0f).setDuration(200 * AchievementUnlocked.animationMultiplier).scaleY(0f).alpha(0f).withEndAction(new Runnable() {
                @Override
                public void run() {
                    animate().setDuration(200 * AchievementUnlocked.animationMultiplier).scaleX(1 / Math.max(scaleX, scaleY)).scaleY(1 / Math.max(scaleX, scaleY)).alpha(1f).withStartAction(new Runnable() {
                        @Override
                        public void run() {
                            setImageDrawable(drawable);
                        }
                    }).start();
                }
            }).start();
        }
    }

    public void fadeDrawable(final Drawable drawable) {
        if (drawable == null) {
            setImageDrawable(null);
            return;
        }
        if (getScaleType() != ScaleType.CENTER_CROP) setScaleType(ScaleType.CENTER_CROP);

        final float scaleX = 3.5f / (getMaxWidth() / drawable.getIntrinsicWidth());
        final float scaleY = 3.5f / (getMaxWidth() / drawable.getIntrinsicHeight());

        if (getDrawable() == null) {
            setImageDrawable(drawable);
            setScaleX(1 / Math.max(scaleX, scaleY));
            setScaleY(1 / Math.max(scaleX, scaleY));
        } else {
            if (drawable.getAlpha() < 255)
                drawable.setAlpha(255);

            animate().setDuration(50 * AchievementUnlocked.animationMultiplier).alpha(0f).withEndAction(new Runnable() {
                @Override
                public void run() {
                    animate().setDuration(50 * AchievementUnlocked.animationMultiplier).alpha(1f).withStartAction(new Runnable() {
                        @Override
                        public void run() {
                            setImageDrawable(drawable);
                        }
                    }).start();
                }
            }).start();
        }
    }

}
