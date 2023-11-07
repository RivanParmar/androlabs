plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(libs.android.tools.build.aapt2.proto)
    implementation(libs.android.tools.common)
    implementation(libs.android.tools.layoutlib)
    implementation(libs.protobuf.java)
}