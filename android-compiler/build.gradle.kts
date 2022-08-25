plugins {
    id("com.android.library")
}

android {
    compileSdk = 33
}

dependencies {
    // r8 dependency
    implementation("org.eclipse.jdt:ecj:3.13.0")
    implementation("com.android.tools:r8:3.3.75")
    implementation(project(":kotlinc"))

    testImplementation("junit:junit:4.13.2")
}