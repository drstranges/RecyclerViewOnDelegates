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
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViewPager()
    }

    private fun initViewPager() {
        mBinding!!.viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int) =
                    when (position) {
                        0 -> UserPageFragment.newInstance()
                        1 -> LocationPageFragment.newInstance()
                        else -> AllInOnePageFragment.newInstance()
                    }

            override fun getPageTitle(position: Int) =
                    when (position) {
                        0 -> getString(R.string.page_users)
                        1 -> getString(R.string.page_locations)
                        else -> getString(R.string.page_all_in_one)
                    }

            override fun getCount() = 3
        }
        mBinding!!.tabLayout.setupWithViewPager(mBinding!!.viewPager)
    }
}
