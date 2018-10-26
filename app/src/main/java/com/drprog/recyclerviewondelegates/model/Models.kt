package com.drprog.recyclerviewondelegates.model

import com.drprog.recyclerviewondelegates.util.IdProvier


interface BaseModel {
    val id: Long
    val sortName: String
}

data class Advertisement(
        val label: String,
        override val id: Long = IdProvier.nextId()
) : BaseModel {
    val image = "http://loremflickr.com/300/100/sport?random=" + this.id
    override val sortName = label
}

data class Location(
        var name: String,
        override val id: Long = IdProvier.nextId()
) : BaseModel {
    val image = "http://loremflickr.com/100/100/" + name + "?random=" + this.id
    override val sortName = name
}

data class User(
        var name: String,
        override val id: Long = IdProvier.nextId()
) : BaseModel {
    val avatar = "http://i.pravatar.cc/80?u=" + name.hashCode()
    override val sortName = name
}