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

package com.rivan.androlabs.core.model.data

enum class ProjectResourceType(
    val serializedName: String,
    // TODO: descriptions should probably be string resources
    val description: String
) {
    Lab(
        serializedName = "Lab",
        description = "An Android Lab project."
    ),
    Project(
        serializedName = "Project",
        description = "An Android app project."
    ),
    // TODO: Remove this if creating libraries won't be supported!
    Library(
        serializedName = "Library",
        description = "An Android library project."
    ),
    JetpackCompose(
        serializedName = "Jetpack Compose",
        description = "A Jetpack Compose Android app project."
    ),
    Unknown(
        serializedName = "Unknown",
        description = "Unknown"
    )
}

fun String?.asProjectResourceType() = when (this) {
    null -> ProjectResourceType.Unknown
    else -> ProjectResourceType.values()
        .firstOrNull { type -> type.serializedName == this}
        ?: ProjectResourceType.Unknown
}