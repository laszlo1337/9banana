package io.finefabric.ninebanana

import android.app.Application
import io.finefabric.ninebanana.entities.MyObjectBox

/**
 * Created by laszlo on 2017-08-29.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        val dataBox = MyObjectBox.builder().androidContext(this).build()
    }
}