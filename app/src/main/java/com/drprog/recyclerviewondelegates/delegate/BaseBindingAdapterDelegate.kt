package com.drprog.recyclerviewondelegates.delegate

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.drprog.recyclerviewondelegates.util.BindingHolder
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

abstract class BaseBindingAdapterDelegate<T : List<*>, VB : ViewDataBinding> : AdapterDelegate<T>() {

    public abstract override fun onCreateViewHolder(parent: ViewGroup): BindingHolder<VB>

    override fun onBindViewHolder(items: T, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val bindingHolder = holder as BindingHolder<VB>
        onBindViewHolder(items, position, bindingHolder)
        bindingHolder.binding.executePendingBindings()
    }

    abstract fun onBindViewHolder(items: T, position: Int, holder: BindingHolder<VB>)
}
