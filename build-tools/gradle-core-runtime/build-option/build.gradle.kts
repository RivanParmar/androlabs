plugins {
    id("androlabs.jvm.library")
}

description = "The Gradle build option parser."

dependencies {
    implementation(project(":build-tools:cli"))
    implementation(project(":build-tools:base-services"))

    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:messaging"))

    implementation(libs.apache.commons.lang3)
}