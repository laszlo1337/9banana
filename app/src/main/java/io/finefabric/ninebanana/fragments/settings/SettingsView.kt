package io.finefabric.ninebanana.fragments.settings

import io.finefabric.ninebanana.base.BaseView

/**
 * Created by Leszek Janiszewski on 2017-10-09.
 */
interface SettingsView : BaseView {
    fun setBananasSwitch(enabled: Boolean)
    fun setKilometersSwitch(enabled: Boolean)
    fun setMilesSwitch(enabled: Boolean)
    fun setDarkModeSwitch(enabled: Boolean)
}