plugins {
    id("androlabs.jvm.library")
}

description = "Logging API"

dependencies {
    api(libs.slf4j)

    implementation(project(":build-tools:base-annotations"))

    implementation(libs.jsr305)
}