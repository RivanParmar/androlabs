plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "A set of generic services and utilities specific for Groovy."

dependencies {
    implementation(project(":build-tools:base-services"))

    implementation(libs.groovy)
    implementation(libs.guava)
}