plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

dependencies {
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:base-annotations"))
    implementation(project(":build-tools:base-services"))

    implementation(libs.asm)
    implementation(libs.asm.commons)
    implementation(libs.asm.tree)
    implementation(libs.groovy)
}
