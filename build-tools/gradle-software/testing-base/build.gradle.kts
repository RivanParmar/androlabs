plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = """Basic testing related plugins, which establish conventions for testing output directories,
and setup basic testing-related features lik a testSuites container and the testing extension.  It provides most of the
testing-related abstract base types and interfaces for things like Test tasks, listeners and filters.

This project is a implementation dependency of many other testing-related subprojects in the Gradle build.
"""

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:worker-processes"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:file-temp"))

    implementation(libs.ant) // only used for DateUtils
    implementation(libs.apache.commons.lang3)
    implementation(libs.asm)
    implementation(libs.commons.io)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.kryo)
    implementation(libs.slf4j)
}