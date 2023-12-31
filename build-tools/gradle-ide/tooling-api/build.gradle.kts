plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Gradle Tooling API - the programmatic API to invoke Gradle"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:wrapper-shared"))
    implementation(project(":build-tools:persistent-cache"))

    implementation(libs.guava)
}