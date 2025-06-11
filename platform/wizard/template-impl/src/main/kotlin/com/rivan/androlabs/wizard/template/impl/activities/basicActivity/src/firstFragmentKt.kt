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

package com.rivan.androlabs.wizard.template.impl.activities.basicActivity.src

import com.rivan.androlabs.wizard.template.api.Language
import com.rivan.androlabs.wizard.template.api.getMaterialComponentName
import com.rivan.androlabs.wizard.template.api.renderIf
import com.rivan.androlabs.wizard.template.impl.activities.common.findViewById
import com.rivan.androlabs.wizard.template.impl.activities.common.importViewBindingClass
import com.rivan.androlabs.wizard.template.impl.activities.common.layoutToViewBindingClass

fun firstFragmentKt(
    packageName: String,
    applicationPackage: String?,
    firstFragmentClass: String,
    secondFragmentClass: String,
    firstFragmentLayoutName: String,
    useAndroidX: Boolean = true,
    isViewBindingSupported: Boolean,
): String {

    val onCreateViewBlock = if (isViewBindingSupported) """
        _binding = ${layoutToViewBindingClass(firstFragmentLayoutName)}.inflate(inflater, container, false)
        return binding.root
    """ else "return inflater.inflate(R.layout.$firstFragmentLayoutName, container, false)"

    return  """package ${packageName}

import android.os.Bundle
import ${getMaterialComponentName("android.support.v4.app.Fragment", useAndroidX)}
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
${renderIf(!isViewBindingSupported) {"""import android.widget.Button"""}}
import androidx.navigation.fragment.findNavController
${importViewBindingClass(isViewBindingSupported, packageName, applicationPackage, firstFragmentLayoutName, Language.Kotlin)}

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class $firstFragmentClass : Fragment() {

${renderIf(isViewBindingSupported) {"""
    private var _binding: ${layoutToViewBindingClass(firstFragmentLayoutName)}? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
"""}}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        $onCreateViewBlock
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ${findViewById(
        Language.Kotlin,
        isViewBindingSupported,
        id = "button_first",
        className = "Button",
        parentView = "view",
    )
    }.setOnClickListener {
            findNavController().navigate(R.id.action_${firstFragmentClass}_to_${secondFragmentClass})
        }
    }

${renderIf(isViewBindingSupported) {"""
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
"""}}
}
"""
}