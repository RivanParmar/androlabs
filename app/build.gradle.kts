plugins {
    id("androidplaygrounds.android.application")
    id("androidplaygrounds.android.application.compose")
}

android {
    defaultConfig {
        applicationId = "com.rivan.androidplaygrounds"
        versionCode = 1
        versionName = "0.0.1" //X.Y.Z; X = Major, Y = Minor, Z = Patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    namespace = "com.rivan.androidplaygrounds"
}

dependencies {

    implementation(project(":feature:recent"))
    implementation(project(":feature:settings"))

    implementation(project(":core:designsystem"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)

    // androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
    configurations.configureEach {
        resolutionStrategy {
            force(libs.junit4)
        }
    }
}