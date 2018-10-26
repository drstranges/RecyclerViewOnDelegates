package com.drprog.recyclerviewondelegates.util.binding

interface OnItemClickListener<I> {

    fun onItemClicked(position: Int, item: I)
}