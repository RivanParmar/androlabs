plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-build-operations"))
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))

    implementation("commons-io:commons-io:2.14.0")
    implementation("org.ow2.asm:asm:9.6")

    implementation(libs.apache.commons.lang)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}