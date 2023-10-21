plugins {
    id("androlabs.jvm.library")
}

description = "Shared classes for projects requiring GPG support"

dependencies {
    api(project(":build-tools:gradle-core-api"))
    api(project(":build-tools:resources"))

    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:functional"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:process-services"))
    implementation(project(":build-tools:resources-http"))

    api("org.bouncycastle:bcpg-jdk18on:1.76")

    implementation(libs.guava)
    implementation(libs.javax.inject)

    implementation(libs.groovy) {
        because("Project.exec() depends on Groovy")
    }
}