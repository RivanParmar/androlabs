plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = """Extends platform-base with base types and interfaces specific to the Java Virtual Machine, including tasks for obtaining a JDK via toolchains, and for compiling and launching Java applications."""

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:gradle-diagnostics"))
    implementation(project(":build-tools:execution"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:jvm-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:normalization-java"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:resources"))
    implementation(project(":build-tools:enterprise-operations"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.native.platform)
}