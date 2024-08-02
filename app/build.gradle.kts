// Adapted from NowInAndroid app
// https://github.com/android/nowinandroid/blob/main/app/build.gradle.kts

import com.rivan.androlabs.AndrolabsBuildType

plugins {
    alias(libs.plugins.androlabs.android.application)
    alias(libs.plugins.androlabs.android.application.compose)
    alias(libs.plugins.androlabs.android.application.flavors)
    alias(libs.plugins.androlabs.android.hilt)
    alias(libs.plugins.androlabs.android.application.firebase)
}

android {
    defaultConfig {
        applicationId = "com.rivan.androlabs"
        versionCode = 1
        versionName = "0.0.1" //X.Y.Z; X = Major, Y = Minor, Z = Patch level

        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner = "com.rivan.androlabs.core.testing.AndrolabsTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = AndrolabsBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = AndrolabsBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play Store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            signingConfig = signingConfigs.named("debug").get()
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    namespace = "com.rivan.androlabs"
}

dependencies {

    implementation(projects.feature.editor)
    implementation(projects.feature.home)
    implementation(projects.feature.nlw)
    implementation(projects.feature.npw)
    implementation(projects.feature.settings)

    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(projects.sync.work)

    androidTestImplementation(projects.core.testing)
    androidTestImplementation(projects.core.network)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.accompanist.testharness)
    androidTestImplementation(kotlin("test"))
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    testImplementation(projects.core.testing)
    testImplementation(projects.core.network)
    testImplementation(libs.androidx.navigation.testing)
    testImplementation(libs.accompanist.testharness)
    testImplementation(kotlin("test"))
    kspTest(libs.hilt.compiler)
}