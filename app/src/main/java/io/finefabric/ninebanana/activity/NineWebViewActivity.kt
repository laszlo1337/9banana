package io.finefabric.ninebanana.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import io.finefabric.ninebanana.R
import io.finefabric.ninebanana.achievements.AchievementData
import io.finefabric.ninebanana.achievements.AchievementUnlocked
import io.finefabric.ninebanana.common.GlideApp
import io.finefabric.ninebanana.common.ObservableWebChromeClient
import io.finefabric.ninebanana.common.addListener
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

        oneMillimetre = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM
                , 1f
                , resources.displayMetrics).toDouble()

        setUpWebView()

        web_view.loadUrl(NINE_GAG_URL)

        web_view.onYScrollChangedListener = { previousPosY, currentPosY ->
            totalDistanceScrolledDown += pxToMm(currentPosY - previousPosY)

//                Log.d("Scroll calculated total", ": " + totalDistanceScrolledDown)
//                Log.d("Scroll pos Y in mm: ", pxToMm(currentPosY).toString())

            distance_chip_bananas.distance.text = String.format("%.1f", totalDistanceScrolledDown)

        }

        setUpBottomSheet()

        presenter = NineActivityPresenter()
        presenter.attachView(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        canDrawAchievements = allowedToDrawOverlays()
        //TODO make proper cookie setting
        CookieManager.getInstance().setCookie(NINE_GAG_URL, "m_dark_theme=1")
    }

    fun pxToMm(px: Int): Double {
        return px / oneMillimetre
    }

    override fun onBackPressed() {
        if (slideUp.isVisible) {
            slideUp.hide()
            return
        }

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

    override fun showAchievement(title: String, subtitle: String, imageUrl: String, bgColor: String
                                 , iconBgColor: String, textColor: String, rounded: Boolean
                                 , large: Boolean, bottomAligned: Boolean, onClickUrl: String) {

        val achievement = AchievementUnlocked(applicationContext)
                .setLarge(large)
                .setRounded(rounded)
                .isTopAligned(!bottomAligned)

        val data = AchievementData()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setBackgroundColor(Color.parseColor(bgColor))
                .setIconBackgroundColor(Color.parseColor(iconBgColor))
                .setTextColor(Color.parseColor(textColor))

        if (onClickUrl.isNotEmpty()) {
            //TODO
        }

        GlideApp.with(this).asGif()
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .addListener(onResourceReady = { _, _, _, _, _ ->
                    achievement.show(data)
                    false
                })
                .circleCrop()
                .into(achievement.iconView)

    }

    fun showAchievement() {
        if (canDrawAchievements) {
            val achievement = AchievementUnlocked(applicationContext).setLarge(true).setRounded(true).isTopAligned(false)
            val data = AchievementData().setTitle("some longer title").setSubtitle(null)
                    .setBackgroundColor(Color.parseColor("#323232"))
                    .setIconBackgroundColor(Color.BLUE).setPopUpOnClickListener { view -> }

            GlideApp.with(this).asGif().load("https://media.giphy.com/media/UogSmj4xDjQZO/giphy.gif")
                    .diskCacheStrategy(DiskCacheStrategy.ALL).listener(object : RequestListener<GifDrawable> {
                override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    achievement.show(data)
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
            }).circleCrop().into(achievement.iconView)
        }
    }

    fun allowedToDrawOverlays(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        return if (!Settings.canDrawOverlays(this)) {
            Snackbar.make(container, getString(R.string.alert_please_allow_overlays), Snackbar.LENGTH_INDEFINITE)
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

    private fun setUpBottomSheet() {
        slideUp = SlideUpBuilder(slide_view)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .withListeners(SlideUp.Listener.Slide { percent ->
                    dim.alpha = (1 - percent / 100)
                })
                .build()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        WebView.setWebContentsDebuggingEnabled(true)
        val chromeClient = ObservableWebChromeClient()

        /**
         * Above 70% progress level looks good when transitioning from splash to WebView
         */
        chromeClient.onProgressChangedListener = { progress ->
            if (progress > 70 && web_view.visibility == View.INVISIBLE) {
                web_view.visibility = View.VISIBLE
            }
            if (progress == 100) Log.d("url", web_view.url)
        }
        web_view.webChromeClient = chromeClient

        /**
         * Allow for 9gag app to be opened after click on "get the app to view all %d comments"
         */
        web_view.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String) {

                val wasHandledAsIntent = handleUrl(url)

                if (wasHandledAsIntent)
//                    view?.stopLoading()

                    super.onLoadResource(view, url)
            }
        }
//        web_view.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0; HUAWEI GRA-L09 Build/HUAWEIGRA-L09) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
//        web_view.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0; HUAWEI GRA-L09 Build/HUAWEIGRA-L09) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.91 Mobile Safari/537.36 OPR/42.9.2246.119945"
        web_view.settings.javaScriptCanOpenWindowsAutomatically = false
        web_view.settings.javaScriptEnabled = true
        web_view.settings.domStorageEnabled = true
    }

    private fun handleUrl(url: String): Boolean {
        if (url.startsWith("intent:")) {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            startActivity(intent)
            web_view.goBack()
            return true
        } else if (url.startsWith("whatsapp:")) {
            val whatsAppIntent = Intent(Intent.ACTION_SEND)
            whatsAppIntent.type = "text/plain"
            whatsAppIntent.`package` = "com.whatsapp"
            val decodedUrl = Uri.decode(url).plus(" Sent via 9banana app")
            whatsAppIntent.putExtra(Intent.EXTRA_TEXT
                    , decodedUrl.substring(decodedUrl.indexOf("=") + 1))
            try {
                startActivity(whatsAppIntent)
                web_view.goBack()
            } catch (ex: android.content.ActivityNotFoundException) {
                Snackbar.make(container
                        , "WhatsApp is not installed."
                        , Snackbar.LENGTH_SHORT)
                        .show()
            }
            return true
        }
        return false
    }
}

