plugins {
    id("androlabs.jvm.library")
}

description = "Process execution abstractions."

dependencies {
    implementation(project(":build-tools:base-services"))

    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:native"))

    implementation(libs.guava)
    implementation(libs.native.platform)
    implementation(libs.slf4j)
}