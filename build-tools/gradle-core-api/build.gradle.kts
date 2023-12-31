plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Public and internal 'core' Gradle APIs that are required by other subprojects"

dependencies {
    api(project(":build-tools:process-services"))

    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:resources"))

    implementation(libs.ant)
    implementation(libs.apache.commons.lang3)
    implementation(libs.groovy)
    implementation(libs.groovy.ant)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}