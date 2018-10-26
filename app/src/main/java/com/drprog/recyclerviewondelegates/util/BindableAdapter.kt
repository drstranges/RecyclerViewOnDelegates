package com.drprog.recyclerviewondelegates.util

import android.support.v7.widget.RecyclerView

import com.drprog.recyclerviewondelegates.model.BaseModel
import com.hannesdorfmann.adapterdelegates3.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager

class BindableAdapter<T> : AbsDelegationAdapter<List<T>> {

    constructor(vararg delegates: AdapterDelegate<List<T>>) : super(AdapterDelegatesManager<List<T>>()) {
        for (delegate in delegates) delegatesManager.addDelegate(delegate)
    }

    constructor(items: List<T>, delegatesManager: AdapterDelegatesManager<List<T>>) : super(delegatesManager) {
        setItems(items)
    }

    constructor(items: List<T>, vararg delegates: AdapterDelegate<List<T>>) : this(*delegates) {
        setItems(items)
    }

    constructor()

    fun setDelegates(delegates: Array<AdapterDelegate<List<T>>>) {
        for (delegate in delegates) delegatesManager.addDelegate(delegate)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun getItemId(position: Int): Long {
        val item = items[position]
        return (item as? BaseModel)?.id ?: RecyclerView.NO_ID
    }
}