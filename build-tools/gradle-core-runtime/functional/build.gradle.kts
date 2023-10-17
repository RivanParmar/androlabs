plugins {
    id("androlabs.jvm.library")
}

dependencies {
    // TODO: The guava library isn't necessary here. It has to be added only because of
    //  `javax.annotation.CheckReturnValue` and other annotations.
    implementation(libs.guava)
}