package io.finefabric.a9meter

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.support.v7.widget.AppCompatImageView
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

/**
 * @author Leszek Janiszewski
 */

class GestureDetectorService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    val windowManager: WindowManager
        get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    var iv: AppCompatImageView? = null

    override fun onCreate() {
        super.onCreate()
        iv = AppCompatImageView(this)
        iv?.setImageResource(R.drawable.ic_launcher_background)

        iv?.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("Click", "From icon")
                    false
                }
                else -> false
            }
        }

        val params: WindowManager.LayoutParams = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                or WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                , PixelFormat.TRANSLUCENT)

        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 100
        params.y = 100

        windowManager.addView(iv, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        iv.let { windowManager.removeView(iv) }
    }
}

