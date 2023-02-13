plugins {
    `kotlin-dsl`
}

group = "com.rivan.androlabs.buildplugins"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "androlabs.android.application.compose"
            implementationClass = "AndroidAppComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "androlabs.android.application"
            implementationClass = "AndroidAppConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "androlabs.android.library.compose"
            implementationClass = "AndroidLibComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "androlabs.android.library"
            implementationClass = "AndroidLibConventionPlugin"
        }
        register("androidFeature") {
            id = "androlabs.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}