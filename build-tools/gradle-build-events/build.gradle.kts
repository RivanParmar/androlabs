plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Implementation of build event services and build event types (work item, tasks, tests, configuration, etc)"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:tooling-api"))

    implementation(libs.guava)
    implementation(libs.jsr305)
}