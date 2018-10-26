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


import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.drextended.actionhandler.ActionHandler
import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.action.ShowToastAction
import com.drprog.recyclerviewondelegates.delegate.AdvertisementDelegate
import com.drprog.recyclerviewondelegates.delegate.UserDelegate
import com.drprog.recyclerviewondelegates.model.ActionType
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.drprog.recyclerviewondelegates.util.DummyDataProvider
import com.drprog.recyclerviewondelegates.util.StickyUserFirstLetterDecorator
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import java.util.*

class UserPageFragment : BasePageFragment() {

    var letterDecorator: StickyUserFirstLetterDecorator? = null

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
//        recyclerView.addItemDecoration(DividerItemDecoration(divider, SPACE_BOTTOM))
        letterDecorator = StickyUserFirstLetterDecorator(
                requireContext().resources.getDimensionPixelSize(R.dimen.stickyOffset)
        )
        recyclerView.addItemDecoration(letterDecorator!!)
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
        var data = getDummyData()
        adapter.items = data
        letterDecorator?.notifyDataSetChanged(data)
        adapter.notifyDataSetChanged()
    }

    private fun getDummyData(): List<BaseModel> {
        var list = ArrayList<BaseModel>()
        list.addAll(DummyDataProvider.users.sortedBy { it.name })

        list.add(0, DummyDataProvider.getAdvertisment(1))
        return list
    }


    companion object {
        fun newInstance(): UserPageFragment {
            return UserPageFragment()
        }
    }

}
