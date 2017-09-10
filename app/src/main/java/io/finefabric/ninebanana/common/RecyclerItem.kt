package io.finefabric.ninebanana.common

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup


/**
 * Created by laszlo on 2017-09-09.
 */
abstract class RecyclerItem<VH : RecyclerViewHolder> {

    val layoutRes: Int
        @LayoutRes
        get() = 0

    fun createViewHolder(adapter: RecyclerAdapter<*, *>, inflater: LayoutInflater?, parent: ViewGroup): VH {
        throw IllegalStateException("onCreateViewHolder()" + MAPPING_ILLEGAL_STATE
                + this.javaClass.simpleName + ".createViewHolder().")
    }

    fun bindViewHolder(adapter: RecyclerAdapter<*, *>, holder: VH, position: Int) {
        throw IllegalStateException("onBindViewHolder()" + MAPPING_ILLEGAL_STATE
                + this.javaClass.simpleName + ".bindViewHolder().")
    }

    companion object {
        private val MAPPING_ILLEGAL_STATE = " is not implemented. If you want RecyclerAdapter creates and binds PolicyInfoItemViewHolder for you, you must override and implement the method "
    }

}