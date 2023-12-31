plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Implementation of configuration model types and annotation metadata handling (Providers, software model, conventions)"

dependencies {
    api(project(":build-tools:gradle-core-api"))
    api(project(":build-tools:problems"))

    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:snapshots"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.asm)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}