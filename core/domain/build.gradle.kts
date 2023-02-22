plugins {
    id("androlabs.android.library")
}

android {
    namespace = "com.rivan.androlabs.core.domain"
}

dependencies {

    implementation(project(":core:model"))
}