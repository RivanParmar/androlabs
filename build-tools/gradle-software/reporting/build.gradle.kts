plugins {
    id("androlabs.jvm.library")
}

description = "Report type classes and plugins for reporting (build dashboard, report container)"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))

    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.jatl)
    implementation(libs.javax.inject)
}