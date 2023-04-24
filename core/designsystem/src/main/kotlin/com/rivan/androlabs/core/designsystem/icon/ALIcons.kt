package com.rivan.androlabs.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.ui.graphics.vector.ImageVector
import com.rivan.androlabs.core.designsystem.R

object ALIcons {
    val About = Icons.Outlined.Info
    val Account = Icons.Outlined.AccountCircle
    val Add = Icons.Filled.Add
    val Back = Icons.Outlined.ArrowBack
    val Favourite = R.drawable.ic_star
    val FavouriteBorder = R.drawable.ic_star_border
    val GeneralSettings = Icons.Outlined.Tune
    val Recent = R.drawable.ic_access_time_filled
    val RecentBorder = R.drawable.ic_access_time
    val Settings = R.drawable.ic_settings
    val SettingsBorder = R.drawable.ic_settings_border
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}