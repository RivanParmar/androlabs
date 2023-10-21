/*
 * Copyright 2021 the original author or authors.
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

package org.gradle.internal.fingerprint.hashing;

import org.gradle.internal.hash.HashCode;

import java.io.IOException;

import javax.annotation.Nullable;

/**
 * Hashes a zip entry (e.g. a class file in a jar, a manifest file, a properties file)
 */
public interface ZipEntryContextHasher {
    /**
     * Returns {@code null} if the zip entry should be ignored.
     */
    @Nullable
    HashCode hash(ZipEntryContext zipEntryContext) throws IOException;
}
