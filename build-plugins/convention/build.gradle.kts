plugins {
    `kotlin-dsl`
}

group = "com.rivan.androidplaygrounds.buildplugins"

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
            id = "androidplaygrounds.android.application.compose"
            implementationClass = "AndroidAppComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "androidplaygrounds.android.application"
            implementationClass = "AndroidAppConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "androidplaygrounds.android.library.compose"
            implementationClass = "AndroidLibComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "androidplaygrounds.android.library"
            implementationClass = "AndroidLibConventionPlugin"
        }
        register("androidFeature") {
            id = "androidplaygrounds.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}