package com.drprog.recyclerviewondelegates.delegate

import android.databinding.ViewDataBinding

import com.drextended.actionhandler.listener.ActionClickListener

abstract class ActionAdapterDelegate<T, VB : ViewDataBinding>(
        actionHandler: ActionClickListener
) : BaseBindingAdapterDelegate<List<T>, VB>() {

    var actionHandler: ActionClickListener

    init {
        this.actionHandler = actionHandler
    }
}