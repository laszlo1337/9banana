package io.finefabric.ninebanana.activity

import io.finefabric.ninebanana.base.BaseView

/**
 * Created by laszlo on 2017-09-01.
 */
interface NineActivityView: BaseView {

    fun showAchievement()

    fun setBananaDistance(distance: String)

    fun setKmDistance(distance: String)

    fun setMiDistance(distance: String)



}