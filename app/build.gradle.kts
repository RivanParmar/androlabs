// Adapted from NowInAndroid app
// https://github.com/android/nowinandroid/blob/main/app/build.gradle.kts

import com.rivan.androlabs.AndrolabsBuildType

plugins {
    id("androlabs.android.application")
    id("androlabs.android.application.compose")
    id("androlabs.android.application.flavors")
    id("androlabs.android.hilt")
    id("androlabs.android.application.firebase")
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
        val debug by getting {
            applicationIdSuffix = AndrolabsBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = false
            applicationIdSuffix = AndrolabsBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play Store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    namespace = "com.rivan.androlabs"
}

dependencies {

    implementation(project(":feature:editor"))
    implementation(project(":feature:home"))
    implementation(project(":feature:nlw"))
    implementation(project(":feature:npw"))
    implementation(project(":feature:settings"))

    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))

    implementation(project(":sync:work"))

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(project(":core:network"))
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.accompanist.testharness)
    androidTestImplementation(kotlin("test"))
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)

    testImplementation(project(":core:testing"))
    testImplementation(project(":core:network"))
    testImplementation(libs.androidx.navigation.testing)
    testImplementation(libs.accompanist.testharness)
    testImplementation(kotlin("test"))
    kaptTest(libs.hilt.compiler)
}