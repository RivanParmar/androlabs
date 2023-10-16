plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))

    implementation("commons-io:commons-io:2.14.0")
    implementation("javax.inject:javax.inject:1")
    implementation("org.ow2.asm:asm:9.6")

    implementation(libs.apache.commons.lang)
    implementation(libs.guava)
    implementation(libs.slf4j)
}