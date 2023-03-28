plugins {
    id("androlabs.android.library")
    id("androlabs.android.library.compose")
    id("androlabs.android.hilt")
}

android {
    namespace = "com.rivan.androlabs.core.testing"
}

dependencies {
    implementation(project(":core:model"))

    api(libs.junit4)
    api(libs.androidx.test.core)

    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.androidx.compose.ui.test)
    api(libs.hilt.android.testing)

    debugApi(libs.androidx.compose.ui.testManifest)
}