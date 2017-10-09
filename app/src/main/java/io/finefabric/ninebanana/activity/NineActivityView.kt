package io.finefabric.ninebanana.activity

import io.finefabric.ninebanana.base.BaseView

/**
 * Created by laszlo on 2017-09-01.
 */
interface NineActivityView: BaseView {

    fun showAchievement(
            title: String,
            subtitle: String,
            imageUrl: String,
            bgColor: String,
            iconBgColor: String,
            textColor: String,
            rounded: Boolean,
            large: Boolean,
            topAligned: Boolean,
            onClickUrl: String
    )

    fun setBananaDistance(distance: String)

    fun setKmDistance(distance: String)

    fun setMiDistance(distance: String)

}