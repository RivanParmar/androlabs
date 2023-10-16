plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:core-runtime:base-annotations"))

    implementation(libs.guava)
}