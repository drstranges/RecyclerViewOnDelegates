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


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.drextended.actionhandler.ActionHandler
import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.action.ShowToastAction
import com.drprog.recyclerviewondelegates.delegate.AdvertisementDelegate
import com.drprog.recyclerviewondelegates.delegate.UserDelegate
import com.drprog.recyclerviewondelegates.model.ActionType
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration.Companion.SPACE_BOTTOM
import com.drprog.recyclerviewondelegates.util.DummyDataProvider
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import java.util.*

class UserPageFragment : BasePageFragment() {

    val actionHandler = ActionHandler.Builder()
            .addAction(ActionType.OPEN, ShowToastAction())
            .addAction(ActionType.MENU, ShowToastAction())
            .build()

    override fun onCreateBinding() {
    }

    override fun onInitRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val divider = requireContext().resources.getDimensionPixelSize(R.dimen.divider_size_default)
        recyclerView.addItemDecoration(DividerItemDecoration(divider, SPACE_BOTTOM))
    }

    override fun createItemDelegates(): Array<AdapterDelegate<List<BaseModel>>> = arrayOf(
            UserDelegate(actionHandler),
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
        list.addAll(DummyDataProvider.users)
        list.add(0, DummyDataProvider.getAdvertisment(1))
        list.add(6, DummyDataProvider.getAdvertisment(2))
        list.add(12, DummyDataProvider.getAdvertisment(3))
        return list
    }


    companion object {
        fun newInstance(): UserPageFragment {
            return UserPageFragment()
        }
    }

}
