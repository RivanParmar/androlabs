plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Tools to work with functional code, including data structures"

dependencies {
    implementation(project(":build-tools:base-annotations"))
}