plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))
    implementation(project(":build-tools:gradle-core-runtime:base-services"))

    implementation("org.codehaus.groovy:groovy:3.0.19")

    implementation(libs.guava)
}