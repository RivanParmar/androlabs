plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Adds support for building Groovy projects"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:build-option"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:worker-processes"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:file-temp"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:jvm-services"))
    implementation(project(":build-tools:workers"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:language-java"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:toolchains-jvm"))

    implementation("org.apache.groovy:groovy-groovydoc:5.0.0-alpha-2")

    implementation(libs.groovy)
    implementation(libs.groovy.ant)
    //implementation(libs.groovyDoc)
    implementation(libs.guava)
    implementation(libs.asm)
    implementation(libs.javax.inject)
}