package io.finefabric.ninebanana

import android.app.Application
import io.finefabric.ninebanana.entities.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

class App: Application() {

    lateinit var dataBox: BoxStore

    override fun onCreate() {
        super.onCreate()

        dataBox = MyObjectBox.builder().androidContext(this).build()
        AndroidObjectBrowser(dataBox).start(this)
        PresetsManager(dataBox)
    }
}