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
    alias(libs.plugins.androlabs.android.library)
    alias(libs.plugins.androlabs.android.hilt)
}

android {
    defaultConfig {
        testInstrumentationRunner = "com.rivan.androlabs.core.testing.AndrolabsTestRunner"
    }

    namespace = "com.rivan.androlabs.sync"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.datastore)
    implementation(projects.core.model)
    implementation(projects.core.network)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)

    testImplementation(projects.core.testing)
    androidTestImplementation(projects.core.testing)

    ksp(libs.hilt.ext.compiler)

    androidTestImplementation(libs.androidx.work.testing)
}