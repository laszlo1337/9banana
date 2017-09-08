package io.finefabric.ninebanana.common

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView

class ObservableWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : WebView(context, attrs, defStyleAttr) {

    var onYScrollChangedListener: ((previousPosY: Int, currentPosY: Int) -> Unit)? = null

    /**
     * This is called in response to an internal scroll in this view (i.e., the
     * view scrolled its own contents). This is typically as a result of
     * {@link #scrollBy(int, int)} or {@link #scrollTo(int, int)} having been
     * called.
     *
     * @param l Current horizontal scroll origin.
     * @param t Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     */
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        onYScrollChangedListener?.invoke(oldt, t)
    }
}

class ObservableWebChromeClient : WebChromeClient() {

    var onProgressChangedListener: ((progress: Int) -> Unit)? = null

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        onProgressChangedListener?.invoke(newProgress)
        super.onProgressChanged(view, newProgress)
    }
}