package com.drprog.recyclerviewondelegates.util.binding

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.ViewGroup
import com.drprog.recyclerviewondelegates.model.BaseModel

/**
 * Created by Roman Donchenko on 11/4/17.
 * Copyright © 2017. All rights reserved.
 */
abstract class SimpleAdapterDelegate<T : BaseModel> : BaseBindingAdapterDelegate<List<T>, ViewDataBinding>() {

    var clickListener: OnItemClickListener<T>? = null

    companion object {
        fun <T : BaseModel> builder() = SimpleAdapterDelegateBuilder<T>()
    }

    override fun isForViewType(items: List<T>, position: Int): Boolean =
            isForViewType(items[position])

    override fun onBindViewHolder(items: List<T>, position: Int, holder: BindingHolder<ViewDataBinding>) {
        clickListener?.let {
            holder.itemView.setOnClickListener { clickListener!!.onItemClicked(position, items[position]) }
        }
        onBind(holder.itemView.context, items[position], holder)
    }

    abstract fun isForViewType(item: BaseModel): Boolean

    abstract fun onBind(context: Context, item: T, holder: BindingHolder<*>)

}
