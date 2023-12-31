plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Source for JavaCompile, JavaExec and Javadoc tasks, it also contains logic for incremental Java compilation"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:enterprise-operations"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:worker-processes"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:file-temp"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:jvm-services"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:workers"))
    implementation(project(":build-tools:snapshots"))
    implementation(project(":build-tools:execution"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:gradle-build-events"))
    implementation(project(":build-tools:tooling-api"))
    implementation(project(":build-tools:toolchains-jvm"))

    implementation(libs.groovy)
    implementation(libs.slf4j)
    implementation(libs.guava)
    implementation(libs.apache.commons.lang3)
    implementation(libs.fastutil)
    implementation(libs.ant)
    implementation(libs.apache.commons.compress)
    implementation(libs.asm)
    implementation(libs.asm.commons)
    implementation(libs.javax.inject)

    //runtimeOnly(project(":java-compiler-plugin"))
}