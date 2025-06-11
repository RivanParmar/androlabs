package com.rivan.androlabs.wizard.template.impl.activities.common.navigation

import com.rivan.androlabs.wizard.template.api.Language
import com.rivan.androlabs.wizard.template.api.RecipeExecutor
import com.rivan.androlabs.wizard.template.api.underscoreToCamelCase
import com.rivan.androlabs.wizard.template.impl.activities.common.addLifecycleDependencies
import com.rivan.androlabs.wizard.template.impl.activities.common.navigation.res.layout.fragmentFirstXml
import com.rivan.androlabs.wizard.template.impl.activities.common.navigation.src.ui.firstFragmentJava
import com.rivan.androlabs.wizard.template.impl.activities.common.navigation.src.ui.firstFragmentKt
import com.rivan.androlabs.wizard.template.impl.activities.common.navigation.src.ui.viewModelJava
import com.rivan.androlabs.wizard.template.impl.activities.common.navigation.src.ui.viewModelKt
import java.io.File

fun RecipeExecutor.saveFragmentAndViewModel(
    resOut: File,
    srcOut: File,
    language: Language,
    packageName: String,
    applicationPackage: String?,
    fragmentPrefix: String,
    useAndroidX: Boolean = true,
    isViewBindingSupported: Boolean,
) {
    val firstFragmentClass = "${underscoreToCamelCase(fragmentPrefix)}Fragment"
    val viewModelClass = "${underscoreToCamelCase(fragmentPrefix)}ViewModel"
    val generateKotlin = language == Language.Kotlin

    save(
        fragmentFirstXml(packageName, fragmentPrefix, firstFragmentClass, useAndroidX),
        resOut.resolve("layout/fragment_${fragmentPrefix}.xml"),
    )
    save(
        if (generateKotlin)
            firstFragmentKt(
                packageName = packageName,
                applicationPackage = applicationPackage,
                firstFragmentClass = firstFragmentClass,
                navFragmentPrefix = fragmentPrefix,
                navViewModelClass = viewModelClass,
                useAndroidX = useAndroidX,
                isViewBindingSupported = isViewBindingSupported,
            )
        else
            firstFragmentJava(
                packageName = packageName,
                applicationPackage = applicationPackage,
                firstFragmentClass = firstFragmentClass,
                navFragmentPrefix = fragmentPrefix,
                navViewModelClass = viewModelClass,
                useAndroidX = useAndroidX,
                isViewBindingSupported = isViewBindingSupported,
            ),
        srcOut.resolve("ui/${fragmentPrefix}/${underscoreToCamelCase(fragmentPrefix)}Fragment.${language.extension}"),
    )
    save(
        if (generateKotlin)
            viewModelKt(packageName, fragmentPrefix, viewModelClass, useAndroidX)
        else
            viewModelJava(packageName, fragmentPrefix, viewModelClass, useAndroidX),
        srcOut.resolve("ui/${fragmentPrefix}/${underscoreToCamelCase(fragmentPrefix)}ViewModel.${language.extension}"),
    )
}

fun RecipeExecutor.navigationDependencies(
    generateKotlin: Boolean,
    useAndroidX: Boolean,
    appCompatVersion: Int,
) {
    addLifecycleDependencies(useAndroidX)
    if (generateKotlin) {
        addDependency("android.arch.navigation:navigation-fragment-ktx:+")
        addDependency("android.arch.navigation:navigation-ui-ktx:+")
    } else {
        addDependency("android.arch.navigation:navigation-fragment:+")
        addDependency("android.arch.navigation:navigation-ui:+")
    }
    /*
    navigation-ui depends on the stable version of design library.
    This is to remove the lint warning for the generated project may not use the same version of
    the support library.
    */
    if (!useAndroidX) {
        addDependency("com.android.support:design:${appCompatVersion}.+")
    }
}