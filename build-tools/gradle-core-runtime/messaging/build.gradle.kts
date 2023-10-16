plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))
    implementation(project(":build-tools:gradle-core-runtime:base-services"))
    implementation(project(":build-tools:gradle-build-operations"))
}