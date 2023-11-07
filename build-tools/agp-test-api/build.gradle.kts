plugins {
    id("androlabs.jvm.library")
}

dependencies {
    implementation(libs.android.tools.annotations)
    implementation(libs.android.tools.common)
    implementation(libs.guava)

    api("com.android.tools.ddms:ddmlib:31.3.0-alpha12")
}