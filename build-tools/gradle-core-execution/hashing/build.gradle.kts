plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Tools for creating secure hashes for files and other content"

dependencies {
    implementation(project(":build-tools:base-annotations"))

    implementation(libs.guava)
}