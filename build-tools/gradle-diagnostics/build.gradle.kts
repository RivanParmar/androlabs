plugins {
    id("androlabs.jvm.library")
}

description = "Contains project diagnostics or report tasks, e.g. help, project report, dependency report and similar"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:snapshots"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:build-option"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.groovy)
    implementation(libs.groovy.json)
    implementation(libs.guava)
    implementation(libs.jatl)
    implementation(libs.javax.inject)
}
