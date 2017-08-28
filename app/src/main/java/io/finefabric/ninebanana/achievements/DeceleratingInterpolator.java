package io.finefabric.ninebanana.achievements;

import android.animation.TimeInterpolator;

/**
 * Created by laszlo on 2017-08-28.
 */
public class DeceleratingInterpolator implements TimeInterpolator {

    private int mBase;
    private final float mLogScale;

    DeceleratingInterpolator(int base) {
        mBase = base;
        mLogScale = 1f / computeLog(1, mBase);
    }

    private static float computeLog(float t, int base) {
        return (float) -Math.pow(base, -t) + 1;
    }

    @Override
    public float getInterpolation(float t) {
        return computeLog(t, mBase) * mLogScale;
    }
}
