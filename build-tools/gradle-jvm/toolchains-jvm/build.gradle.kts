plugins {
    id("androlabs.jvm.library")
}

description = "Adds support for using JVM toolchains in projects"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:gradle-diagnostics"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:jvm-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:resources"))

    implementation(libs.commons.io)
    implementation(libs.apache.commons.lang3)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.native.platform) {
        because("Required for SystemInfo")
    }
}