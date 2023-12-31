plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Included build controller and composite build infrastructure"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:gradle-plugin-use"))

    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}