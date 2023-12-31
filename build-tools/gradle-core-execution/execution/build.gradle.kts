plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Execution engine that takes a unit of work and makes it happen"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:build-cache"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:enterprise-operations")) {
        because("Adds generic build operations for the execution engine")
    }

    implementation(libs.apache.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}