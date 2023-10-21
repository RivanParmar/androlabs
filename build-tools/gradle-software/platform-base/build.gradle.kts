plugins {
    id("androlabs.jvm.library")
}

description = """Provides general purpose base types and interfaces for modeling projects, and provides runtime and language support."""

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:workers"))
    implementation(project(":build-tools:execution"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}