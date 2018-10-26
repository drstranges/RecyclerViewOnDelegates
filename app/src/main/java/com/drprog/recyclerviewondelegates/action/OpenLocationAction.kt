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
package com.drprog.recyclerviewondelegates.action

import android.content.Context
import android.view.View
import android.widget.Toast

import com.drextended.actionhandler.action.BaseAction
import com.drprog.recyclerviewondelegates.model.Location

/**
 * Action to handle click by location
 */
class OpenLocationAction : BaseAction<Location>() {

    override fun isModelAccepted(model: Any): Boolean {
        return model is Location
    }

    override fun onFireAction(context: Context, view: View?, actionType: String?, model: Location?) {
        Toast.makeText(context, "Click by Location: " + model!!.name, Toast.LENGTH_SHORT).show()
    }
}
