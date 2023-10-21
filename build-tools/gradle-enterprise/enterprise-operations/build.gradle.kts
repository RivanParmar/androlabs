plugins {
    id("androlabs.jvm.library")
}

description = "Build operations consumed by the Gradle Enterprise plugin"

dependencies {
    api(project(":build-tools:gradle-build-operations"))
    api(project(":build-tools:enterprise-workers"))

    implementation(project(":build-tools:base-annotations"))
    implementation(libs.jsr305)
}