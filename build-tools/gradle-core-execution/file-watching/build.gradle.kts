plugins {
    id("androlabs.jvm.library")
}

description = "File system watchers for keeping the VFS up-to-date"

dependencies {
    api(project(":build-tools:snapshots"))

    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:functional"))

    implementation(libs.guava)
    implementation(libs.native.platform)
    implementation(libs.native.platform.file.events)
    implementation(libs.slf4j)
}