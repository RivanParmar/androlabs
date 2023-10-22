plugins {
    id("androlabs.jvm.library")
}

description = """JVM-specific testing functionality, including the Test type and support for configuring options for and detecting
tests written in various JVM testing frameworks. This project "extends" the testing-base project by sub-typing many
of its abstractions with JVM-specific abstractions or implementations.

This project is a implementation dependency of many other testing-related subprojects in the Gradle build, and is a necessary
dependency for any projects working directly with Test tasks.
"""

dependencies {
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:file-temp"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:toolchains-jvm"))
    implementation(project(":build-tools:testing-jvm-infrastructure"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.groovy)
    implementation(libs.groovy.xml)
    implementation(libs.guava)
    implementation(libs.asm)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}