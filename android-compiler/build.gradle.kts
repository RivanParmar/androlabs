plugins {
    id("com.android.library")
}

android {
    compileSdk = 33
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // r8 dependency
    implementation("org.eclipse.jdt:ecj:3.13.0")
    implementation("com.android.tools:r8:3.3.75")
    implementation(project(":kotlinc"))
    implementation(project(":project-utils"))

    implementation("com.google.googlejavaformat:google-java-format:1.15.0")
    implementation(libs.androidx.annotation)

    testImplementation("junit:junit:4.13.2")
}