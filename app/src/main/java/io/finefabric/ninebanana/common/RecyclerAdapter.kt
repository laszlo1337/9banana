package io.finefabric.ninebanana.common

import android.view.LayoutInflater
import android.view.ViewGroup


/**
 * Created by laszlo on 2017-09-09.
 */

class RecyclerAdapter<VH : RecyclerViewHolder, T : RecyclerItem<VH>> : BaseRecyclerViewAdapter<VH>() {

    private val NO_ID = -1
    private var items: MutableList<T>? = null
    private val typeInstances = HashMap<Int, T>()
    private var autoMap = false

    private var layoutInflater: LayoutInflater? = null

    init {
        items = ArrayList(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val item = getViewTypeInstance(viewType)
        if (item == null || !autoMap) {
            throw IllegalStateException("ViewType instance not found for viewType " + viewType +
                    ". Override this method or implement the AutoMap properly.")
        }

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return item.createViewHolder(this, layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (!autoMap) {
            throw IllegalStateException("AutoMap is not active, this method cannot be called."
                    + " Override this method or implement the AutoMap properly.")
        }

        val item = getItem(position)
        item?.bindViewHolder(this, holder, position)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return 0
        // Map the view type if not done yet
        mapViewTypeFrom(item)
        autoMap = true
        return item.layoutRes
    }

    private fun mapViewTypeFrom(item: T?) {
        if (item != null && !typeInstances.containsKey(item.layoutRes)) {
            typeInstances.put(item.layoutRes, item)
        }
    }

    private fun getItem(position: Int): T? {
        return if (position < 0 || position >= itemCount) null
        else
            items!![position]
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return item?.hashCode()?.toLong() ?: NO_ID.toLong()
    }

    private fun getViewTypeInstance(viewType: Int): T? {
        return typeInstances.get(viewType)
    }

    fun add(model: T) {
        items!!.add(model)
        notifyItemInserted(items!!.size - 1)
    }

    fun add(model: T, position: Int) {
        items!!.add(position, model)
        notifyItemInserted(position)
    }

    fun remove(model: T) {
        items!!.remove(model)
        notifyItemRemoved(items!!.indexOf(model))
    }

    fun remove(position: Int) {
        items!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addAll(models: List<T>) {
        this.items!!.addAll(models)
        notifyItemRangeInserted(this.items!!.size - 1, this.items!!.size - 1 + (models.size - 1))
    }

    fun addAll(models: List<T>, position: Int) {
        this.items!!.addAll(position, models)
        notifyItemRangeInserted(position, this.items!!.size - 1 + (models.size - 1))
    }

    var all: List<T>
        get() = ArrayList(items)
        set(models) {
            this.items!!.clear()
            this.items!!.addAll(models)
            notifyDataSetChanged()
        }

}