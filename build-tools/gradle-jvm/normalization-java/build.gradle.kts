plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "API extraction for Java"

dependencies {
    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:hashing"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:snapshots"))

    implementation(libs.asm)
    implementation(libs.commons.io)
    implementation(libs.guava)
    implementation(libs.slf4j)
}