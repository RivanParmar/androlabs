plugins {
    id("androidplaygrounds.android.library")
    id("androidplaygrounds.android.library.compose")
}

android {
    namespace = "com.rivan.androidplaygrounds.core.designsystem"
}

dependencies {
    // TODO: Use version catalogs for declaring below dependency
    implementation("androidx.compose.ui:ui:1.3.0")
    api(libs.androidx.compose.material.iconsExtended)
}