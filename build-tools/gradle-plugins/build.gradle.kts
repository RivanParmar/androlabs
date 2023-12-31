plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:persistent-cache"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:workers"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:plugins-groovy"))
    implementation(project(":build-tools:plugins-java"))
    implementation(project(":build-tools:plugins-java-base"))
    implementation(project(":build-tools:plugins-jvm-test-suite"))
    implementation(project(":build-tools:publish"))
    implementation(project(":build-tools:language-java"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:gradle-diagnostics"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:testing-jvm"))
    implementation(project(":build-tools:toolchains-jvm"))
    implementation(project(":build-tools:snapshots"))
    implementation(project(":build-tools:execution")) {
        because("We need it for BuildOutputCleanupRegistry")
    }

    implementation(libs.ant)
    implementation(libs.apache.commons.lang3)
    implementation(libs.groovy)
    implementation(libs.groovy.templates)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}
