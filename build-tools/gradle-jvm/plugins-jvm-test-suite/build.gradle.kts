plugins {
    id("androlabs.jvm.library")
}

description = "Contains the JVM Test Suite plugin"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:language-java"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:plugins-java-base"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:testing-jvm"))
    implementation(project(":build-tools:tooling-api"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.ant)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}