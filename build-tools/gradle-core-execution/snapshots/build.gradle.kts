plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Tools to take immutable, comparable snapshots of files and other things"

dependencies {
    api(project(":build-tools:files"))
    api(project(":build-tools:hashing"))

    implementation(project(":build-tools:base-annotations"))

    implementation(libs.guava)
    implementation(libs.slf4j)
}