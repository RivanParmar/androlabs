plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:base-annotations"))

    implementation(libs.guava)
}