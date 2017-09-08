package io.finefabric.ninebanana.common

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * @author Leszek Janiszewski
 */

fun <T> GlideRequest<T>.addListener(

        onResourceReady: ((resource: T?, model: Any?, target: Target<T>?, dataSource: DataSource?, isFirstResource: Boolean) -> Boolean)? = null,
        onLoadFailed: ((e: GlideException?, model: Any?, target: Target<T>?, isFirstResource: Boolean) -> Boolean)? = null): GlideRequest<T> {

    this.listener(object : RequestListener<T> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<T>?, isFirstResource: Boolean): Boolean {
            return onLoadFailed?.invoke(e, model, target, isFirstResource) ?: false
        }

        override fun onResourceReady(resource: T, model: Any?, target: Target<T>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            return onResourceReady?.invoke(resource, model, target, dataSource, isFirstResource) ?: false
        }
    })

    return this
}