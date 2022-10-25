plugins {
    id("com.android.library")
}

android {
    compileSdk = 33
}

dependencies {

    //implementation(project(":core-navigation"))

    implementation(libs.androidx.compose.material3.windowSizeClass)
}