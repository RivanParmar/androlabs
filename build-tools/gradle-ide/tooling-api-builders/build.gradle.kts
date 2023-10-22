plugins {
    id("androlabs.jvm.library")
}

description = "Provider-side implementation for running tooling model builders"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:base-services-groovy")) // for 'Specs'
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:gradle-build-events"))
    implementation(project(":build-tools:gradle-composite-build"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:launcher"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:resources"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:testing-jvm"))
    implementation(project(":build-tools:tooling-api"))
    implementation(project(":build-tools:workers"))

    implementation(libs.commons.io)
    implementation(libs.groovy) // for 'Closure'
    implementation(libs.gson)
    implementation(libs.guava)
}