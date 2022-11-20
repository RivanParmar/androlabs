package com.rivan.androidplaygrounds.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector
import com.rivan.androidplaygrounds.core.designsystem.R

object APIcons {
    // TODO: Remove 'MoreVert' if not needed
    val MoreVert = Icons.Default.MoreVert
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