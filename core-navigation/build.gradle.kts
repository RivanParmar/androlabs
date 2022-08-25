@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
}

android {
    compileSdk = 33
}

dependencies {
    api(libs.androidx.navigation.compose)
}