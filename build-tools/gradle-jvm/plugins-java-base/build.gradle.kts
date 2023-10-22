plugins {
    id("androlabs.jvm.library")
}

description = "Contains a basic JVM plugin used to compile, test, and assemble Java source; often applied by other JVM plugins (though named java-base, jvm-base would be a more proper name)."

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
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:testing-jvm"))
    implementation(project(":build-tools:toolchains-jvm"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.groovy)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}