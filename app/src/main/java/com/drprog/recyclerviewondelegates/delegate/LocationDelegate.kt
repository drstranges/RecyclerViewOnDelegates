/*
 *  Copyright Roman Donchenko. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.drprog.recyclerviewondelegates.delegate

import android.view.LayoutInflater
import android.view.ViewGroup

import com.drextended.actionhandler.listener.ActionClickListener
import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.databinding.ItemLocationBinding
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.drprog.recyclerviewondelegates.model.Location
import com.drprog.recyclerviewondelegates.util.binding.ActionAdapterDelegate
import com.drprog.recyclerviewondelegates.util.binding.BindingHolder

/**
 * Item Delegate to display Location item
 */
class LocationDelegate(actionHandler: ActionClickListener) : ActionAdapterDelegate<BaseModel, ItemLocationBinding>(actionHandler) {

    override fun isForViewType(items: List<BaseModel>, position: Int): Boolean {
        return items[position] is Location
    }

    override fun onCreateViewHolder(parent: ViewGroup): BindingHolder<ItemLocationBinding> {
        return BindingHolder.newInstance(R.layout.item_location, LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(items: List<BaseModel>, position: Int, holder: BindingHolder<ItemLocationBinding>) {
        val location = items[position] as Location
        holder.binding.location = location
        holder.binding.actionHandler = actionHandler
    }

}
