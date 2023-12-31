plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Implementation of messaging between Gradle processes"

dependencies {
    implementation(project(":build-tools:base-services"))

    implementation(libs.fastutil)
    implementation(libs.guava)
    implementation(libs.kryo)
    implementation(libs.slf4j)
}