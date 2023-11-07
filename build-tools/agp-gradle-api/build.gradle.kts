plugins {
    id("androlabs.jvm.library")
}

dependencies {
    api(project(":build-tools:gradle-core-api"))

    implementation(project(":build-tools:agp"))
    implementation(project(":build-tools:agp-test-api"))
    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:logging-api"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:testing-jvm"))
    implementation(project(":build-tools:workers"))

    implementation(libs.android.tools.annotations)
    implementation(libs.asm)
    implementation(libs.guava)
}