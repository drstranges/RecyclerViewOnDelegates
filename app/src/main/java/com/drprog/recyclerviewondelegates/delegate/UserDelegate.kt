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
import com.drprog.recyclerviewondelegates.databinding.ItemUserBinding
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.drprog.recyclerviewondelegates.model.User
import com.drprog.recyclerviewondelegates.util.BindingHolder

/**
 * Item Delegate to display User item
 */
class UserDelegate(actionHandler: ActionClickListener) : ActionAdapterDelegate<BaseModel, ItemUserBinding>(actionHandler) {

    override fun isForViewType(items: List<BaseModel>, position: Int): Boolean {
        return items[position] is User
    }

    override fun onCreateViewHolder(parent: ViewGroup): BindingHolder<ItemUserBinding> {
        return BindingHolder.newInstance(R.layout.item_user, LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(items: List<BaseModel>, position: Int, holder: BindingHolder<ItemUserBinding>) {
        val user = items[position] as User
        holder.binding.user = user
        holder.binding.actionHandler = actionHandler
    }
}
