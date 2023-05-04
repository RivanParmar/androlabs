plugins {
    id("androlabs.java.library")
}

dependencies {
    implementation(project(":build-tools:jaxp:xml"))
}

/*java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}*/