package io.finefabric.ninebanana.fragments.settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.finefabric.ninebanana.R

/**
 * Created by laszlo on 2017-08-29.
 */
class SettingsFragment : Fragment(), SettingsView {

    private val settingsPresenter = SettingsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        settingsPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun setKilometersSwitch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMilesSwitch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBananasSwitch(enabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setDarkModeSwitch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}