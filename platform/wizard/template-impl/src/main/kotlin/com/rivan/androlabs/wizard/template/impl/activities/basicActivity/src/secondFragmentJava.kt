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

fun secondFragmentJava(
    packageName: String,
    applicationPackage: String?,
    useAndroidX: Boolean,
    firstFragmentClass: String,
    secondFragmentClass: String,
    secondFragmentLayoutName: String,
    isViewBindingSupported: Boolean,
): String {
    val onCreateViewBlock = if (isViewBindingSupported) """
      binding = ${layoutToViewBindingClass(secondFragmentLayoutName)}.inflate(inflater, container, false);
      return binding.getRoot();
    """ else "return inflater.inflate(R.layout.$secondFragmentLayoutName, container, false);"

    return """package ${packageName};

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ${getMaterialComponentName("android.support.annotation.NonNull", useAndroidX)};
import ${getMaterialComponentName("android.support.v4.app.Fragment", useAndroidX)};
import androidx.navigation.fragment.NavHostFragment;
${importViewBindingClass(isViewBindingSupported, packageName, applicationPackage, secondFragmentLayoutName, Language.Java)}

public class $secondFragmentClass extends Fragment {

${renderIf(isViewBindingSupported) {"""
    private ${layoutToViewBindingClass(secondFragmentLayoutName)} binding;
"""}}

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        $onCreateViewBlock
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ${findViewById(
        Language.Java,
        isViewBindingSupported,
        id = "button_second",
        parentView = "view",
    )
    }.setOnClickListener(v ->
                NavHostFragment.findNavController(${secondFragmentClass}.this)
                        .navigate(R.id.action_${secondFragmentClass}_to_${firstFragmentClass});
        });
    }

${renderIf(isViewBindingSupported) {"""
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
"""}}

}
"""
}