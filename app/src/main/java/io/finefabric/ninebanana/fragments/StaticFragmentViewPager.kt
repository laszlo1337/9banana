package io.finefabric.ninebanana.fragments

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by laszlo on 2017-08-30.
 */
class StaticFragmentViewPager(context: Context?, attrs: AttributeSet?) : ViewPager(context, attrs) {

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}