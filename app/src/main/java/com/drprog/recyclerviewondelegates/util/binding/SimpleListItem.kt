package com.drprog.recyclerviewondelegates.util.binding

import com.drprog.recyclerviewondelegates.model.BaseModel

data class SimpleListItem<T>(var value: T? = null, val tag: String = TAG_DEFAULT) : BaseModel {
    override val sortName: String = tag

    override var id = value?.hashCode()?.toLong() ?: tag.hashCode().toLong()

    companion object {
        const val TAG_DEFAULT = "DEFAULT"
        fun isInstance(obj: Any, clazz: Class<*>): Boolean =
                obj is SimpleListItem<*> && clazz.isInstance(obj.value)

        fun isInstance(obj: Any, tag: String): Boolean =
                obj is SimpleListItem<*> && tag == obj.tag
    }
}