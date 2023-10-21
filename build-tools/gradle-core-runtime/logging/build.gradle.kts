plugins {
    id("androlabs.jvm.library")
}

description = "Logging infrastructure"

dependencies {
    api(project(":build-tools:logging-api"))

    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:enterprise-workers"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:cli"))
    implementation(project(":build-tools:build-option"))
    implementation(project(":build-tools:problems"))

    implementation(project(":build-tools:native"))

    implementation(libs.ant)
    implementation(libs.apache.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.jansi)
}