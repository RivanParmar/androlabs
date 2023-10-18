plugins {
    id("androlabs.jvm.library")
}

description = "A set of generic services and utilities."

dependencies {
    api(project(":build-tools:base-annotations"))
    api(project(":build-tools:hashing"))
    api(project(":build-tools:gradle-build-operations"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.asm)
    implementation(libs.commons.io)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}