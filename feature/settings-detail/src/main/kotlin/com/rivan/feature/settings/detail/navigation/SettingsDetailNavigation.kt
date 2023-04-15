/*
 * Copyright 2023 Rivan Parmar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.feature.settings.detail.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rivan.androlabs.core.decoder.StringDecoder
import com.rivan.feature.settings.detail.SettingsDetailRoute

internal const val settingsDetailIdArg = "settingId"

internal class RootSettingsOptionsArgs(val settingsDetailId: String) {
    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[settingsDetailIdArg])))
}

fun NavController.navigateToSettingsDetail(settingsDetailId: String) {
    val encodedId = Uri.encode(settingsDetailId)
    this.navigate("setting_detail_route/$encodedId")
}

fun NavGraphBuilder.settingsDetailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "setting_detail_route/{$settingsDetailIdArg}",
        arguments = listOf(
            navArgument(settingsDetailIdArg) { type = NavType.StringType }
        )
    ) {
        SettingsDetailRoute(onBackClick)
    }
}