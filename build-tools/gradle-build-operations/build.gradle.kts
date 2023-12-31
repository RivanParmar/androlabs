plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Build operations are our way to inspect the process of executing a build"

dependencies {
    implementation(project(":build-tools:base-annotations"))

    implementation(libs.slf4j)
}