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
package com.drprog.recyclerviewondelegates.ui


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.databinding.FragmentPageBinding
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.drprog.recyclerviewondelegates.util.binding.BindableAdapter
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

abstract class BasePageFragment : Fragment() {

    protected lateinit var binding: FragmentPageBinding
    protected var adapter: BindableAdapter<BaseModel> = BindableAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val delegates = createItemDelegates()
        adapter.setDelegates(delegates)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page, container, false)
        onInitRecyclerView(binding.recyclerView)
        onCreateBinding()
        return binding.root
    }

    protected fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    protected abstract fun onCreateBinding()

    protected abstract fun onInitRecyclerView(recyclerView: RecyclerView)

    abstract fun createItemDelegates(): Array<AdapterDelegate<List<BaseModel>>>
}
