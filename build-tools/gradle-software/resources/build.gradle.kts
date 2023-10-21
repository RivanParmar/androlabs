plugins {
    id("androlabs.jvm.library")
}

description = "A set of general-purpose resource abstractions"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:native"))

    implementation(libs.commons.io)
    implementation(libs.guava)
    implementation(libs.slf4j)
}
