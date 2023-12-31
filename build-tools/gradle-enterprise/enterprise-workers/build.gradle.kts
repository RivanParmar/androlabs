plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Gradle Enterprise plugin dependencies that also need to be exposed to workers"

dependencies {
    implementation(project(":build-tools:base-annotations"))

    implementation(libs.jsr305)
}