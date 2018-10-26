package com.drprog.recyclerviewondelegates.util

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Recycler View Holder to use with data mBinding
 *
 * @param VB The type of view data binding
 */
class BindingHolder<VB : ViewDataBinding>(
        val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        @JvmStatic
        fun <VB : ViewDataBinding> newInstance(
                @LayoutRes layoutId: Int,
                inflater: LayoutInflater,
                parent: ViewGroup?,
                attachToParent: Boolean
        ): BindingHolder<VB> {
            val vb = DataBindingUtil.inflate<VB>(inflater, layoutId, parent, attachToParent)
            return BindingHolder(vb)
        }
    }

}
