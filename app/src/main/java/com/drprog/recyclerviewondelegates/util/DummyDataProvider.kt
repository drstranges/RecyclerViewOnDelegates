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
package com.drprog.recyclerviewondelegates.util

import com.drprog.recyclerviewondelegates.model.Advertisement
import com.drprog.recyclerviewondelegates.model.Location
import com.drprog.recyclerviewondelegates.model.User
import java.util.*

/**
 * Provider of fake data
 */
object DummyDataProvider {

    val users: List<User>
        get() = Arrays.asList<User>(
                User("Tarik Dickerson"),
                User("Hilel Hart"),
                User("Dane Warner"),
                User("Lamar Gross"),
                User("Driscoll Lancaster"),
                User("Finn Kelly"),
                User("Quinlan Burt"),
                User("Ryan Dotson"),
                User("Zachary Benjamin"),
                User("Connor Merrill"),
                User(" Jeremy Alford"),
                User("Demetrius Hodge"),
                User("Troy Ware"),
                User("Jared Villarreal"),
                User("Slade Romero"),
                User("Keane Franks")
        )

    val locations: List<Location>
        get() = Arrays.asList<Location>(
                Location("Amsterdam"),
                Location("Paris"),
                Location("Rome"),
                Location("London"),
                Location("New York"),
                Location("Los Angeles"),
                Location("Sydney"),
                Location("Copenhagen"),
                Location("Dubai"),
                Location("Berlin"),
                Location("Budapest"),
                Location("Tokyo")
        )

    fun getAdvertisment(index: Int): Advertisement {
        return Advertisement("$index This is Advertisment")
    }
}