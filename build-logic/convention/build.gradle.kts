import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.rivan.androlabs.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
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
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "androlabs.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "androlabs.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "androlabs.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "androlabs.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidFirebase") {
            id = "androlabs.android.application.firebase"
            implementationClass = "AndroidAppFirebaseConventionPlugin"
        }
        register("androidFlavors") {
            id = "androlabs.android.application.flavors"
            implementationClass = "AndroidAppFlavorsConventionPlugin"
        }
        register("jvmLibrary") {
            id = "androlabs.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}