plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = """Contains some base and shared classes for JVM language support, like AbstractCompile class and BaseForkOptions classes,
JVM-specific dependencies blocks and JVM test suite interfaces."""

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:workers"))

    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}