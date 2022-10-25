plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(project(":build-tools:kotlinc"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
