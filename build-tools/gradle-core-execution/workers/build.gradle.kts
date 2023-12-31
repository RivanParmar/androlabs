plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Infrastructure for starting and managing worker processes"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:worker-processes"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:resources"))
    implementation(project(":build-tools:snapshots"))

    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}