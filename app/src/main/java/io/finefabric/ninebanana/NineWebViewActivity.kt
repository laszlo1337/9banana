package io.finefabric.ninebanana

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class NineWebViewActivity : AppCompatActivity() {

    private val NINE_GAG_URL: String = "http://m.9gag.com"

    private var calculatedMm    = 0f
    private var backPressedOnce = false

    private var totalDistanceScrolledDown = 0f

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculatedMm = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, resources.displayMetrics)

        val chromeClient = ObservableWebChromeClient()

        web_view.webChromeClient = chromeClient
        web_view.webViewClient = WebViewClient()

        web_view.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0; HUAWEI GRA-L09 Build/HUAWEIGRA-L09) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
        web_view.settings.javaScriptCanOpenWindowsAutomatically = false
        web_view.settings.javaScriptEnabled = true
        web_view.settings.domStorageEnabled = true

        chromeClient.setOnPageLoadedListener(object : ObservableWebChromeClient.OnPageLoadedListener {
            override fun onPageLoaded() {
                web_view.visibility = View.VISIBLE
            }
        })

        web_view.setOnYScrollChangedListener(object : ObservableWebView.OnScrollChangedListener {
            override fun onYScrollChange(previousPosY: Int, currentPosY: Int) {
                totalDistanceScrolledDown += pxToMm(currentPosY - previousPosY)

                Log.d("Scroll calculated total", ": " + totalDistanceScrolledDown)
                Log.d("Scroll pos Y in mm: ", pxToMm(currentPosY).toString())
                if (totalDistanceScrolledDown > 1000 && totalDistanceScrolledDown < 1200){
                    showAchievement(null,null)
                }
            }
        })

        web_view.loadUrl(NINE_GAG_URL)

    }

    fun pxToMm(px: Int): Float {
        return px / calculatedMm
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }

            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
            backPressedOnce = true
            Handler().postDelayed({ kotlin.run { backPressedOnce = false } }, 2000)
        }
    }

    fun showAchievement(title: String?, subtitle: String?){

    }

    private val REQUEST_CODE: Int = 9

    fun allowedToDrawOverlays(): Boolean{
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        return if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
            startActivityForResult(intent, REQUEST_CODE)
            false
        } else {
            true
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {

            }
        }
    }
}