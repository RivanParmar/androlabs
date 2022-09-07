plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    implementation(libs.androidx.annotation)

    implementation(libs.kotlinx.coroutines.android)

    compileOnly(files("libs/android-stubs.jar"))

    implementation("org.json:json:20200518")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}