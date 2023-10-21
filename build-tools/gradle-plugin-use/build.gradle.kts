plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:build-option"))
    implementation(project(":build-tools:problems"))

    implementation(libs.groovy)
    implementation(libs.guava)
}
