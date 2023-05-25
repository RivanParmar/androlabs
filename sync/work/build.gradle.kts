/*
 * Copyright 2023 Rivan Parmar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("androlabs.android.library")
    id("androlabs.android.hilt")
}

android {
    defaultConfig {
        testInstrumentationRunner = "com.rivan.androlabs.core.testing.AndrolabsTestRunner"
    }

    namespace = "com.rivan.androlabs.sync"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)

    testImplementation(project(":core:testing"))
    androidTestImplementation(project(":core:testing"))

    kapt(libs.hilt.ext.compiler)

    androidTestImplementation(libs.androidx.work.testing)
}