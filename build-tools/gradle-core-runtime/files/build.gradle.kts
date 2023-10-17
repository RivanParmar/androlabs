plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))
    implementation(project(":build-tools:gradle-core-runtime:functional"))

    implementation(libs.guava)
    implementation(libs.slf4j)
}