plugins {
    id("androlabs.jvm.library")
}

description = "Infrastructure that bootstraps a worker process"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:process-services"))
}