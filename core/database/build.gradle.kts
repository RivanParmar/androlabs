plugins {
    id("androlabs.android.library")
    id("androlabs.android.hilt")
    id("androlabs.android.room")
}

android {
    namespace = "com.rivan.androlabs.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.datetime)
}