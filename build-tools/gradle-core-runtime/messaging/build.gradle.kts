plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))
    implementation(project(":build-tools:gradle-core-runtime:base-services"))
    implementation(project(":build-tools:gradle-build-operations"))

    implementation(libs.guava)
    implementation(libs.slf4j)

    implementation("com.esotericsoftware:kryo:5.5.0")
    implementation("it.unimi.dsi:fastutil:8.5.12")
}