plugins {
    alias(libs.plugins.androlabs.android.feature)
    alias(libs.plugins.androlabs.android.library.compose)
}

android {
    namespace = "com.rivan.androlabs.feature.settings"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)

    implementation(libs.accompanist.adaptive)
}