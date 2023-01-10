plugins {
    id("androidplaygrounds.android.library")
    id("androidplaygrounds.android.library.compose")
}

android {
    namespace = "com.rivan.androidplaygrounds.core.ui"
}

dependencies {
    api(libs.androidx.compose.ui.tooling.preview)
}