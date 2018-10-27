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
import com.drprog.recyclerviewondelegates.databinding.ItemUserBinding
import com.drprog.recyclerviewondelegates.delegate.AdvertisementDelegate
import com.drprog.recyclerviewondelegates.delegate.LocationDelegate
import com.drprog.recyclerviewondelegates.model.ActionType
import com.drprog.recyclerviewondelegates.model.BaseModel
import com.drprog.recyclerviewondelegates.model.User
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration
import com.drprog.recyclerviewondelegates.util.DividerItemDecoration.Companion.SPACE_BOTTOM
import com.drprog.recyclerviewondelegates.util.DummyDataProvider
import com.drprog.recyclerviewondelegates.util.binding.SimpleAdapterDelegate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import java.util.*

class AllInOnePageFragment : BasePageFragment() {

    val actionHandler = ActionHandler.Builder()
            .addAction(ActionType.OPEN, ShowToastAction())
            .addAction(ActionType.MENU, ShowToastAction())
            .build()

    override fun onCreateBinding() {
    }

    override fun onInitRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val divider = requireContext().resources.getDimensionPixelSize(R.dimen.divider_size_default)
        recyclerView.addItemDecoration(DividerItemDecoration(divider, SPACE_BOTTOM))
    }

    override fun createItemDelegates(): Array<AdapterDelegate<List<BaseModel>>> = arrayOf(
            SimpleAdapterDelegate.builder<BaseModel>()
                    .with(R.layout.item_user)
                    .forClass(User::class.java)
                    .onBind { context, item, holder ->
                        (holder.binding as? ItemUserBinding)?.apply {
                            this.user = item as User
                            this.actionHandler = this@AllInOnePageFragment.actionHandler
                        }
                    }
                    .build(),
//            UserDelegate(actionHandler),
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
        list.addAll(DummyDataProvider.users)

        list.shuffle()

        list.add(0, DummyDataProvider.getAdvertisment(1))
        list.add(6, DummyDataProvider.getAdvertisment(2))
        list.add(12, DummyDataProvider.getAdvertisment(3))
        list.add(22, DummyDataProvider.getAdvertisment(4))
        list.add(30, DummyDataProvider.getAdvertisment(5))
        return list
    }


    companion object {
        fun newInstance(): AllInOnePageFragment {
            return AllInOnePageFragment()
        }
    }

}
