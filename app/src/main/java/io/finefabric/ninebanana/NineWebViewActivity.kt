package io.finefabric.ninebanana

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.distance_view.view.*


class NineWebViewActivity : AppCompatActivity() {

    private val NINE_GAG_URL: String = "http://m.9gag.com"

    private val REQUEST_CODE: Int = 9

    private var oneMillimetre = 0.0 //one millimetre calculated with display density

    private var backPressedOnce = false

    private var totalDistanceScrolledDown = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneMillimetre = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, resources.displayMetrics).toDouble()

        setUpWebView()


        web_view.loadUrl(NINE_GAG_URL)

        web_view.setOnYScrollChangedListener(object : ObservableWebView.OnScrollChangedListener {
            override fun onYScrollChange(previousPosY: Int, currentPosY: Int) {
                totalDistanceScrolledDown += pxToMm(currentPosY - previousPosY)

                Log.d("Scroll calculated total", ": " + totalDistanceScrolledDown)
                Log.d("Scroll pos Y in mm: ", pxToMm(currentPosY).toString())

                banana.distance.text = String.format("%.1f", totalDistanceScrolledDown)

            }
        })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }



    fun pxToMm(px: Int): Double {
        return px / oneMillimetre
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }

            Snackbar.make(container, "Press again to exit", Snackbar.LENGTH_LONG).setAction("MENU", View.OnClickListener { view -> 1+1 }).show()
            backPressedOnce = true
            Handler().postDelayed({ kotlin.run { backPressedOnce = false } }, 2000)
        }
    }

//    fun showAchievement(title: String?, subtitle: String?){
//
//    }



    fun allowedToDrawOverlays(): Boolean {
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

    fun calculateAndDisplayDistances(distanceInMm: Double) {

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebView(){
        val chromeClient = ObservableWebChromeClient()
        chromeClient.setOnPageLoadedListener(object : ObservableWebChromeClient.OnPageLoadedListener {
            override fun onPageLoaded() {
                web_view.visibility = View.VISIBLE
            }
        })
        web_view.webChromeClient = chromeClient
        web_view.webViewClient = WebViewClient()
        web_view.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0; HUAWEI GRA-L09 Build/HUAWEIGRA-L09) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
        web_view.settings.javaScriptCanOpenWindowsAutomatically = false
        web_view.settings.javaScriptEnabled = true
        web_view.settings.domStorageEnabled = true
    }
}