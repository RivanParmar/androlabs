plugins {
    id("java-library")
}

dependencies {
    api(files("libs/nb-javac-17.0.0.0-api.jar"))
    api(files("libs/nb-javac-17.0.0.0.jar"))

    api("com.github.marschall:zipfilesystem-standalone:1.0.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    tragetCompatibility = JavaVersion.VERSION_1_8
}
