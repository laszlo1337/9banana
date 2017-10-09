package io.finefabric.ninebanana.fragments.settings

import io.finefabric.ninebanana.base.BasePresenter

/**
 * Created by Leszek Janiszewski on 2017-10-09.
 */
class SettingsPresenter : BasePresenter<SettingsView>() {

    override fun onViewAttached() {
    }

    override fun onViewDetached() {
    }

    private fun setSwitches() {
        view?.setBananasSwitch(true)
    }
}