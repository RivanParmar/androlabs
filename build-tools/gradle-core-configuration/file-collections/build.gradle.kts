plugins {
    id("androlabs.jvm.library")
}

description = "Implementation of types that represent containers of files"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:native"))

    implementation(libs.commons.io)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}