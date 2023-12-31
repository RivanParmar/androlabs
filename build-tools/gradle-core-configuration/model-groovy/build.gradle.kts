plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = "Groovy specific adaptations to the model management."

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:base-services-groovy"))

    implementation(libs.groovy)
    implementation(libs.guava)
}