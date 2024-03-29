/*
 * Copyright 2022 the original author or authors.
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

package org.gradle.jvm.toolchain.internal;

import org.gradle.jvm.toolchain.JavaToolchainRequest;
import org.gradle.jvm.toolchain.JavaToolchainSpec;
import org.gradle.platform.BuildPlatform;

public class DefaultJavaToolchainRequest implements JavaToolchainRequest {

    private final JavaToolchainSpec spec;

    private final BuildPlatform buildPlatform;

    public DefaultJavaToolchainRequest(JavaToolchainSpec spec, BuildPlatform buildPlatform) {
        this.spec = spec;
        this.buildPlatform = buildPlatform;
    }

    @Override
    public JavaToolchainSpec getJavaToolchainSpec() {
        return spec;
    }

    @Override
    public BuildPlatform getBuildPlatform() {
        return buildPlatform;
    }
}
