plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Utility code shared between the wrapper and the Gradle distribution"

dependencies {
    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:files")) {
        because("We need org.gradle.internal.file.PathTraversalChecker")
    }
}