buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

// TODO: Remove this annotation once Gradle 8.1 is available
// See: https://github.com/gradle/gradle/issues/22797#issuecomment-1422049473
@Suppress("DSL_SCOPE_VIOLATION")
// Lists all plugins used throughout the project without applying them.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}

// TODO: Remove this once above issue is fixed or https://github.com/gradle/gradle/issues/20131 is fixed
println("")