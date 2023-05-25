package com.rivan.androlabs

@Suppress("unused")
enum class AndrolabsBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE
}