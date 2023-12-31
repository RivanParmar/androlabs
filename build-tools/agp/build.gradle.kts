plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

dependencies {
    implementation(project(":build-tools:agp-test-api"))
    implementation(project(":build-tools:manifest-merger"))

    implementation("commons-codec:commons-codec:1.16.0")

    implementation(libs.android.signflinger)
    implementation(libs.android.zipflinger)
    implementation(libs.android.tools.annotations)
    implementation(libs.android.tools.build.aapt2.proto)
    implementation(libs.android.tools.build.apksig)
    implementation(libs.android.tools.build.apkzlib)
    implementation(libs.android.tools.common)
    implementation(libs.android.tools.layoutlib)
    implementation(libs.android.tools.r8)
    implementation(libs.android.tools.sdk.common) {
        isTransitive = false
    }
    implementation(libs.android.tools.sdklib)
    implementation(libs.asm)
    implementation(libs.asm.commons)
    implementation(libs.guava)
    implementation(libs.javax.inject)
}