plugins {
    alias(libs.plugins.androlabs.android.library)
    alias(libs.plugins.androlabs.android.library.compose)
    alias(libs.plugins.androlabs.android.hilt)
}

android {
    namespace = "com.rivan.androlabs.core.testing"
}

dependencies {
    api(projects.core.model)

    api(libs.junit4)
    api(libs.androidx.test.core)

    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.androidx.compose.ui.test)
    api(libs.hilt.android.testing)

    debugApi(libs.androidx.compose.ui.testManifest)
}