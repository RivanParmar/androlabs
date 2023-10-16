plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))

    implementation("org.ow2.asm:asm:9.6")
    implementation("org.slf4j:slf4j-api:2.0.9")

    implementation(libs.apache.commons.lang)
    implementation(libs.guava)
}