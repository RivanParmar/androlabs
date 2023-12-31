plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Base plugin for the maven and ivy publish plugins. Defines the publishing extension."

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:base-services-groovy")) // for 'Specs'
    implementation(project(":build-tools:dependency-management"))

    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.apache.commons.lang3)
    implementation(libs.gson)
    implementation(libs.javax.inject)
}