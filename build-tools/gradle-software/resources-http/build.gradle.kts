plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Implementation for interacting with repositories over HTTP"

dependencies {
    api(project(":build-tools:resources"))

    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:logging"))

    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("org.samba.jcifs:jcifs:1.3.14-kohsuke-1")

    implementation(libs.apache.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.guava)
    implementation(libs.slf4j)
}
