plugins {
    id("androidplaygrounds.android.feature")
    id("androidplaygrounds.android.library.compose")
}

android {
    namespace = "com.rivan.androidplaygrounds.feature.recent"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
}