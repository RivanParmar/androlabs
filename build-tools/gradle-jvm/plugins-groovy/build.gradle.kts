plugins {
    id("androlabs.jvm.library")
}

description = "Contains plugins for building Groovy projects."

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:language-groovy"))
    implementation(project(":build-tools:language-java"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:plugins-java"))
    implementation(project(":build-tools:plugins-java-base"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:toolchains-jvm"))

    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}