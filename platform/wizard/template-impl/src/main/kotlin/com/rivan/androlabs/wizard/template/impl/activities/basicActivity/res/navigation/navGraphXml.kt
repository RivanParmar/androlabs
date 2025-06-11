/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.wizard.template.impl.activities.basicActivity.res.navigation

fun navGraphXml(
    packageName:String,
    firstFragmentClass: String,
    secondFragmentClass: String,
    firstFragmentLayoutName: String,
    secondFragmentLayoutName: String,
    navGraphName: String,
) =
    """<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/${navGraphName}"
    app:startDestination="@id/$firstFragmentClass">

    <fragment
        android:id="@+id/$firstFragmentClass"
        android:name="$packageName.$firstFragmentClass"
        android:label="@string/first_fragment_label"
        tools:layout="@layout/$firstFragmentLayoutName" >

        <action
            android:id="@+id/action_${firstFragmentClass}_to_$secondFragmentClass"
            app:destination="@id/$secondFragmentClass" />
    </fragment>
    <fragment
        android:id="@+id/$secondFragmentClass"
        android:name="$packageName.$secondFragmentClass"
        android:label="@string/second_fragment_label"
        tools:layout="@layout/$secondFragmentLayoutName" >

        <action
            android:id="@+id/action_${secondFragmentClass}_to_$firstFragmentClass"
            app:destination="@id/$firstFragmentClass" />
    </fragment>
</navigation>"""