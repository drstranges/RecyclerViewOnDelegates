package com.drprog.recyclerviewondelegates.util

object IdProvier {

    private var id = 1L

    fun nextId() = id++
}