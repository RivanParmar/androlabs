plugins {
    id("androlabs.jvm.library")
}

description = "Utilities for working with temporary files & directories"

dependencies {
    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:base-services"))

    implementation(libs.javax.inject)
}