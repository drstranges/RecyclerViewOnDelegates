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


import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.drextended.actionhandler.ActionHandler
import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.action.OpenLocationAction
import com.drprog.recyclerviewondelegates.action.ShowToastAction
import com.drprog.recyclerviewondelegates.delegate.AdvertisementDelegate
import com.drprog.recyclerviewondelegates.delegate.LocationDelegate
import com.drprog.recyclerviewondelegates.model.ActionType
import com.drprog.recyclerviewondelegates.model.Advertisement
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration.Companion.SPACE_BOTTOM
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration.Companion.SPACE_LEFT
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration.Companion.SPACE_RIGHT
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration.Companion.SPACE_TOP
import com.drprog.recyclerviewondelegates.util.DummyDataProvider
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import java.util.*

class LocationPageFragment : BasePageFragment() {

    val actionHandler = ActionHandler.Builder()
            .addAction(ActionType.OPEN, OpenLocationAction())
            .addAction(ActionType.MENU, ShowToastAction())
            .build()

    override fun onCreateBinding() {
    }

    override fun onInitRecyclerView(recyclerView: RecyclerView) {
        recyclerView.hasFixedSize()
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItems().get(position) is Advertisement) 2 else 1
            }

        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        val divider = requireContext().resources.getDimensionPixelSize(R.dimen.divider_size_default)
        recyclerView.addItemDecoration(DividerItemDecoration(divider, SPACE_LEFT or SPACE_TOP or SPACE_RIGHT or SPACE_BOTTOM))
    }

    override fun createItemDelegates(): Array<AdapterDelegate<List<BaseModel>>> = arrayOf(
            LocationDelegate(actionHandler),
            AdvertisementDelegate(actionHandler)
    )

    override fun onStart() {
        super.onStart()
        if (adapter.items == null) loadData()
    }

    private fun loadData() {
        adapter.items = getDummyData()
        adapter.notifyDataSetChanged()
    }

    private fun getDummyData(): List<BaseModel> {
        val list = ArrayList<BaseModel>()
        list.addAll(DummyDataProvider.locations)
        list.add(0, DummyDataProvider.getAdvertisment(4))
        list.add(9, DummyDataProvider.getAdvertisment(5))
        return list
    }

    companion object {
        fun newInstance(): LocationPageFragment {
            return LocationPageFragment()
        }
    }

}
