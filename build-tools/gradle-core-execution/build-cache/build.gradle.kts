plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Implementation of build cache controller and factories"

dependencies {
    api(project(":build-tools:build-cache-base"))
    api(project(":build-tools:snapshots"))

    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:file-temp"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:resources"))
    implementation(project(":build-tools:logging"))

    implementation(libs.apache.commons.compress)
    implementation(libs.commons.io)
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}