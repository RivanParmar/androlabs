plugins {
    id("java-library")
}
apply(plugin = "kotlin")

configurations.implementation {
    exclude(group = "org.jetbrains", module = "annotations")
}

dependencies {
    implementation(project(":build-tools:jaxp:xml"))
    implementation(project(":build-tools:jaxp:internal"))
    implementation("com.github.marschall:zipfilesystem-standalone:1.0.1")
    implementation(libs.androidx.annotation)

    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.10")

    api("org.jetbrains.trove4j:trove4j:20160824")
    api(files("libs/kotlin-compiler-embeddable-1.7.20-Beta.jar"))

    compileOnly(files("libs/the-unsafe.jar"))
    compileOnly(files("libs/android-stubs.jar"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}