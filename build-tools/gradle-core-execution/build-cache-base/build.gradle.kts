plugins {
    id("androlabs.jvm.library")
}

description = "Common shared build cache classes"

dependencies {
    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:files"))

    implementation(libs.slf4j)
}