plugins {
    alias(libs.plugins.androlabs.android.library)
    alias(libs.plugins.androlabs.android.library.compose)
}

android {
    namespace = "com.rivan.androlabs.core.designsystem"
}

dependencies {
//    implementation(libs.androidx.core.ktx)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
}