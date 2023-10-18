plugins {
    id("androlabs.jvm.library")
}

description = "Implementation of messaging between Gradle processes"

dependencies {
    implementation(project(":build-tools:base-services"))

    implementation(libs.guava)
    implementation(libs.slf4j)

    implementation("com.esotericsoftware:kryo:5.5.0")
    implementation("it.unimi.dsi:fastutil:8.5.12")
}