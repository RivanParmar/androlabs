/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.wizard.template.api

import java.net.URL

// TODO: Allow setting images from drawable resources. And maybe use vector images instead of
//   regular pngs/jpgs.
/**
 * Stores information about a thumb which should be displayed in galleries such as New Activity
 * Gallery.
 */
open class Thumb(val path: () -> URL) {
    /**
     * Represents absence of thumb (null object pattern).
     */
    companion object NoThumb: Thumb({ URL("file://noThumb") })
}