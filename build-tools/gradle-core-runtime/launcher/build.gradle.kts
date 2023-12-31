plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Implementation for launching, controlling and communicating with Gradle Daemon from CLI and TAPI"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:cli"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:build-option"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:execution"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:jvm-services"))
    implementation(project(":build-tools:gradle-build-events"))
    implementation(project(":build-tools:tooling-api"))
    implementation(project(":build-tools:file-watching"))
    implementation(project(":build-tools:problems"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.asm)
    implementation(libs.commons.io)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.slf4j)

    runtimeOnly(libs.apache.commons.lang3)
    runtimeOnly(libs.asm)
    runtimeOnly(libs.commons.io)
    runtimeOnly(libs.slf4j)
}