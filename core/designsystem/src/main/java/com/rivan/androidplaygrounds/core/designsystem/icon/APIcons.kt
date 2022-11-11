package com.rivan.androidplaygrounds.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

object APIcons {

}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}