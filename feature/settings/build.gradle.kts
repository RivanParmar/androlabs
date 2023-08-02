plugins {
    id("androlabs.android.feature")
    id("androlabs.android.library.compose")
}

android {
    namespace = "com.rivan.androlabs.feature.settings"
}

dependencies {
    implementation(libs.accompanist.adaptive)
}