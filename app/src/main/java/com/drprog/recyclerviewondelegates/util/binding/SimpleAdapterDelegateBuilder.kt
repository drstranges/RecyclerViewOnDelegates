package com.drprog.recyclerviewondelegates.util.binding

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

/**
 * Created by Roman Donchenko on 05.11.2017.
 * Copyright © 2017. All rights reserved.
 */
class SimpleAdapterDelegateBuilder<T : BaseModel> {

    private var itemResId: Int = 0
    private var forClass: Class<out T>? = null
    private var forSimpleItemTag: String? = null
    private var binder: ((context: Context, item: T, holder: BindingHolder<*>) -> Unit)? = null
    private var clickListener: OnItemClickListener<T>? = null
    private var isForItem: ((item: BaseModel) -> Boolean)? = null

    fun with(@LayoutRes itemResId: Int): SimpleAdapterDelegateBuilder<T> {
        this.itemResId = itemResId
        return this
    }

    fun forClass(forClass: Class<out T>): SimpleAdapterDelegateBuilder<T> {
        this.forClass = forClass
        return this
    }

    fun forItem(isForItem: (item: BaseModel) -> Boolean): SimpleAdapterDelegateBuilder<T> {
        this.isForItem = isForItem
        return this
    }

    fun forSimpleItem(tag: String): SimpleAdapterDelegateBuilder<T> {
        this.forSimpleItemTag = tag
        return this
    }

    fun onBind(binder: (context: Context, item: T, holder: BindingHolder<*>) -> Unit): SimpleAdapterDelegateBuilder<T> {
        this.binder = binder
        return this
    }

    fun onClick(listener: (position: Int, item: T) -> Unit): SimpleAdapterDelegateBuilder<T> {
        this.clickListener = object : OnItemClickListener<T> {
            override fun onItemClicked(position: Int, item: T) {
                listener.invoke(position, item)
            }
        }
        return this
    }

    fun build(): AdapterDelegate<List<T>> {
        val delegate = object : SimpleAdapterDelegate<T>() {

            override fun isForViewType(item: BaseModel): Boolean =
                    (forSimpleItemTag != null && item is SimpleListItem<*> && item.tag == forSimpleItemTag)
                            || (isForItem == null && forClass == null && forSimpleItemTag == null)
                            || (isForItem != null && isForItem!!.invoke(item))
                            || (forClass != null && forClass!!.isInstance(item))

            override fun onCreateViewHolder(parent: ViewGroup): BindingHolder<ViewDataBinding> =
                    BindingHolder.newInstance(itemResId, LayoutInflater.from(parent.context), parent, false)

            override fun onBind(context: Context, item: T, holder: BindingHolder<*>) {
                binder?.invoke(context, item, holder)
            }
        }
        if (clickListener != null) delegate.clickListener = clickListener
        return delegate
    }

}