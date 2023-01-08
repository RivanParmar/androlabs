plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    api(files("libs/nb-javac-17.0.0.0-api.jar"))
    api(files("libs/nb-javac-17.0.0.0.jar"))
    api(files("libs/javac-new.jar"))

    api("com.github.marschall:zipfilesystem-standalone:1.0.1")

    implementation(files("libs/jrt-fs.jar"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
