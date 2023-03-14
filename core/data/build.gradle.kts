plugins {
    id("androlabs.android.library")
}

android {
    namespace = "com.rivan.androlabs.core.data"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
}