package io.finefabric.ninebanana.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import io.finefabric.ninebanana.R
import io.finefabric.ninebanana.achievements.AchievementData
import io.finefabric.ninebanana.achievements.AchievementUnlocked
import io.finefabric.ninebanana.util.ObservableWebChromeClient
import io.finefabric.ninebanana.util.ObservableWebView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_distance_view.view.*
import kotlinx.android.synthetic.main.slide_up_fragment_layout.*


class NineWebViewActivity : AppCompatActivity(), NineActivityView {

    private val NINE_GAG_URL: String = "http://m.9gag.com"

    private val REQUEST_CODE: Int = 9

    private var oneMillimetre = 0.0 //one millimetre based on display density

    private var backPressedOnce = false

    private var totalDistanceScrolledDown = 0.0

    private lateinit var slideUp: SlideUp

    private var canDrawAchievements = false

    private lateinit var presenter: NineActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneMillimetre = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, resources.displayMetrics).toDouble()

        setUpWebView()

        web_view.loadUrl(NINE_GAG_URL)

        web_view.setOnYScrollChangedListener(object : ObservableWebView.OnScrollChangedListener {
            override fun onYScrollChange(previousPosY: Int, currentPosY: Int) {
                totalDistanceScrolledDown += pxToMm(currentPosY - previousPosY)

//                Log.d("Scroll calculated total", ": " + totalDistanceScrolledDown)
//                Log.d("Scroll pos Y in mm: ", pxToMm(currentPosY).toString())

                distance_chip_bananas.distance.text = String.format("%.1f", totalDistanceScrolledDown)

            }
        })

        setUpFragments()

        presenter = NineActivityPresenter()
        presenter.attachView(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        canDrawAchievements = allowedToDrawOverlays()
    }

    fun pxToMm(px: Int): Double {
        return px / oneMillimetre
    }

    override fun onBackPressed() {
        if (slideUp.isVisible) {
            slideUp.hide()
            return
        }
        showAchievement(null, null)
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }

            Snackbar.make(container, "Press again to exit", Snackbar.LENGTH_SHORT)
                    .setAction("MENU", { _ ->
                        slideUp.show()
//                        showAchievement(null, null)
                    }).show()
            backPressedOnce = true
            Handler().postDelayed({ kotlin.run { backPressedOnce = false } }, 2000)
        }
    }

    override fun setBananaDistance(distance: String) {
        distance_chip_bananas.distance.text = distance
    }

    override fun setKmDistance(distance: String) {
        distance_chip_kilometers.distance.text = distance
    }

    override fun setMiDistance(distance: String) {
        distance_chip_miles.distance.text = distance
    }

    override fun showAchievement() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showAchievement(title: String?, subtitle: String?) {
        if (canDrawAchievements) {
            var drawable: Drawable? = null
            Glide.with(this).asDrawable().load("https://upload.wikimedia.org/wikipedia/commons/d/de/Windows_live_square.JPG")
            val data1 = AchievementData().setTitle("Achievement unlocked!").setSubtitle("testing 1 2 3 - is this achievement achieved?").setIcon(drawable).setBackgroundColor(Color.parseColor("#313131"))
            val data = AchievementData().setTitle("\"the_8cm_guy\"").setSubtitle("let's not talk about it, just keep scrolling...").setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round))
            val achievementUnlocked = AchievementUnlocked(applicationContext)
            achievementUnlocked.setRounded(true)
            achievementUnlocked.setDismissible(true)
            achievementUnlocked.setLarge(false)

            achievementUnlocked.show(data, data1)
        }
    }

    fun allowedToDrawOverlays(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        return if (!Settings.canDrawOverlays(this)) {
            Snackbar.make(container, "Please allow system overlays for displaying achievements", Snackbar.LENGTH_INDEFINITE)
                    .setAction("ALLOW", { _ ->
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
                        startActivityForResult(intent, REQUEST_CODE)
                    }).show()
            false
        } else {
            true
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                canDrawAchievements = true
            }
        }
    }

    private fun setUpFragments() {
        slideUp = SlideUpBuilder(slide_view)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .withListeners(SlideUp.Listener.Slide { percent -> dim.alpha = (1 - percent / 100) })
                .build()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        val chromeClient = ObservableWebChromeClient()
        chromeClient.setOnPageLoadedListener(object : ObservableWebChromeClient.OnProgressChangedListener {
            override fun onProgressChanged(progress: Int) {
                /*
                 * Above 70% progress level looks good when transitioning from splash to WebView
                 */
                Log.d("Progress = ", progress.toString())
                if (progress > 70 && web_view.visibility == View.INVISIBLE) {
                    web_view.visibility = View.VISIBLE
                }
                if (progress == 100) {
                    Log.d("URL = ", web_view.url)
                }
            }
        })
        web_view.webChromeClient = chromeClient
        web_view.webViewClient = WebViewClient()
//        web_view.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0; HUAWEI GRA-L09 Build/HUAWEIGRA-L09) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
//        web_view.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0; HUAWEI GRA-L09 Build/HUAWEIGRA-L09) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.91 Mobile Safari/537.36 OPR/42.9.2246.119945"
        web_view.settings.javaScriptCanOpenWindowsAutomatically = false
        web_view.settings.javaScriptEnabled = true
        web_view.settings.domStorageEnabled = true
    }
}

