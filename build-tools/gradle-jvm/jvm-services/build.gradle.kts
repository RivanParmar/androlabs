plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "JVM invocation and inspection abstractions"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:file-temp"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))

    implementation(libs.asm)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}