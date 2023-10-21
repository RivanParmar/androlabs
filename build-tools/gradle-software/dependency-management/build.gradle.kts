plugins {
    id("androlabs.jvm.library")
}

description = """This project contains most of the dependency management logic of Gradle:
    |* the resolution engine,
    |* how to retrieve and process dependencies and their metadata,
    |* the dependency locking and verification implementations.
    |
    |DSL facing APIs are to be found in 'core-api'""".trimMargin()

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:build-option"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:file-temp"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:build-cache"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:resources"))
    implementation(project(":build-tools:resources-http"))
    implementation(project(":build-tools:snapshots"))
    implementation(project(":build-tools:execution"))
    implementation(project(":build-tools:security"))
    implementation(project(":build-tools:wrapper-shared"))

    implementation("org.apache.httpcomponents:httpcore:4.4.16")
    implementation("org.apache.ivy:ivy:2.5.2")
    implementation("org.apache.maven:maven-settings-builder:4.0.0-alpha-7")

    implementation(libs.ant)
    implementation(libs.apache.commons.lang3)
    implementation(libs.asm)
    implementation(libs.asm.commons)
    implementation(libs.commons.io)
    implementation(libs.fastutil)
    implementation(libs.groovy)
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.slf4j)
}