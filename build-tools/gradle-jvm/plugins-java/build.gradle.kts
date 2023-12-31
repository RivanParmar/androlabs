plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Contains the Java plugin, and its supporting classes.  This plugin is used as the basis for building a Java library or application by more specific plugins, and is sometimes applied by other JVM language projects."

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:gradle-diagnostics"))
    implementation(project(":build-tools:execution"))
    implementation(project(":build-tools:file-collections"))
    implementation(project(":build-tools:language-java"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:plugins-java-base"))
    implementation(project(":build-tools:plugins-jvm-test-suite"))
    implementation(project(":build-tools:publish"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:testing-jvm"))
    implementation(project(":build-tools:toolchains-jvm"))

    implementation(libs.ant)
    implementation(libs.apache.commons.lang3)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}
