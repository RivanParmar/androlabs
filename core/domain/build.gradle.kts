plugins {
    alias(libs.plugins.androlabs.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.rivan.androlabs.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)

//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.kotlinx.datetime)

//    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
}