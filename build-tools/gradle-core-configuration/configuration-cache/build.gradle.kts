plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Configuration cache implementation"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:gradle-composite-build"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:execution"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:file-temp"))
    implementation(project(":build-tools:file-watching"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:hashing"))
    implementation(project(":build-tools:launcher"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:gradle-plugin-use"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:publish"))
    implementation(project(":build-tools:resources"))
    implementation(project(":build-tools:resources-http"))

    implementation(project(":build-tools:workers"))

    implementation(project(":build-tools:tooling-api"))
    implementation(project(":build-tools:gradle-build-events"))
    implementation(project(":build-tools:build-option"))

    implementation("io.usethesource:capsule:0.6.3")

    implementation(libs.asm)
    implementation(libs.fastutil)
    implementation(libs.groovy)
    implementation(libs.groovy.json)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}