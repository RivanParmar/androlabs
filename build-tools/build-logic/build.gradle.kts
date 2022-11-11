plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(libs.androidx.annotation)

    api(project(":build-tools:jaxp:internal"))
    api(project(":build-tools:jaxp:xml"))
    api(project(":build-tools:kotlinc"))

    implementation(project(":build-tools:project"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
