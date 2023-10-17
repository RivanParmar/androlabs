plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(project(":build-tools:gradle-core-runtime:base-annotations"))
    implementation(project(":build-tools:gradle-core-runtime:base-services"))

    // TODO: The guava library isn't necessary here. It has to be added only because of
    //  `javax.annotation.CheckReturnValue`.
    implementation(libs.guava)
    implementation(libs.javax.inject)
}