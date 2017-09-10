package io.finefabric.ninebanana.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


/**
 * Created by laszlo on 2017-09-09.
 */
open class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH? {
        return null
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

}