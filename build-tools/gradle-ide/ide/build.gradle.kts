plugins {
    id("androlabs.jvm.library")
}

description = "Plugins and model builders for integration with Eclipse and IntelliJ IDEs"

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:base-services-groovy"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:gradle-plugins"))
    implementation(project(":build-tools:plugins-java"))
    implementation(project(":build-tools:plugins-java-base"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:language-java"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:tooling-api"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:testing-jvm"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.groovy)
    implementation(libs.groovy.xml)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}