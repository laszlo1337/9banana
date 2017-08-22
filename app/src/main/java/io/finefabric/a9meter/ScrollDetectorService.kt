package io.finefabric.a9meter

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView

/**
 * @author Leszek Janiszewski
 */
class ScrollDetectorService : Service() {
    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val windowManager: WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val iv = ImageView(this)

    override fun onCreate() {
        super.onCreate()
        iv.setImageResource(R.drawable.ic_launcher_background)

        val params: WindowManager.LayoutParams = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.TYPE_PHONE
                , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
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