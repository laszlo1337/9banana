package io.finefabric.ninebanana.activity

import io.finefabric.ninebanana.base.BaseView

/**
 * Created by laszlo on 2017-09-01.
 */
interface NineActivityView: BaseView {

    fun showAchievement(title: String, subtitle:String, imageUrl: String, iconBgColor: String, bgColor: String, textColor: String, rounded: Boolean, large: Boolean)

    fun setBananaDistance(distance: String)

    fun setKmDistance(distance: String)

    fun setMiDistance(distance: String)



}