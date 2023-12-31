plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "This project contains various native operating system integration utilities"

dependencies {
    api(project(":build-tools:files"))

    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:file-temp"))

    implementation(libs.commons.io)
    implementation(libs.guava)
    implementation(libs.jansi)
    implementation(libs.javax.inject)
    implementation(libs.native.platform)
    implementation(libs.native.platform.file.events)
    implementation(libs.slf4j)
}