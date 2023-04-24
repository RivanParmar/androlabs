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

package com.rivan.androlabs.feature.settings

import androidx.annotation.StringRes
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.designsystem.icon.Icon.ImageVectorIcon

object Routes {
    const val GENERAL_ROUTE = "general"
    const val ABOUT_ROUTE = "about"
}

enum class SettingsDashboardCategories(
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val icon: ImageVectorIcon,
    val route: String
) {
    GENERAL(
        title = R.string.settings_general,
        description = R.string.settings_general_desc,
        icon = ImageVectorIcon(ALIcons.GeneralSettings),
        route = Routes.GENERAL_ROUTE
    ),
    ABOUT(
        title = R.string.settings_about,
        description = R.string.settings_about_desc,
        icon = ImageVectorIcon(ALIcons.About),
        route = Routes.ABOUT_ROUTE
    )
}