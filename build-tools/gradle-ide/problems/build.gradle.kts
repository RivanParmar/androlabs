plugins {
    id("androlabs.jvm.library")
}

description = """A problems description API
    |
    |This project provides base classes to describe problems and their
    |solutions, in a way that enforces the creation of good error messages.
    |
    |It's a stripped down version of the original code available
    |at https://github.com/melix/jdoctor/
""".trimMargin()

dependencies {
    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:enterprise-operations"))

    implementation(libs.guava)
    implementation(libs.javax.inject)
}