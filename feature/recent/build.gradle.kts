plugins {
    id("androlabs.android.feature")
    id("androlabs.android.library.compose")
}

android {
    namespace = "com.rivan.androlabs.feature.recent"
}

dependencies {
    implementation(project(":core:ui"))
}