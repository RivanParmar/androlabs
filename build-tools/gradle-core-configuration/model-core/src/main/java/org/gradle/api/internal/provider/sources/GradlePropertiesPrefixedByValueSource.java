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

package org.gradle.api.internal.provider.sources;

import org.gradle.api.internal.properties.GradleProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.inject.Inject;

public abstract class GradlePropertiesPrefixedByValueSource extends MapWithPrefixedKeysValueSource<GradlePropertiesPrefixedByValueSource.Parameters> {
    public interface Parameters extends MapWithPrefixedKeysValueSource.Parameters {
    }

    @Inject
    protected abstract GradleProperties getGradleProperties();

    @Override
    protected Stream<Map.Entry<String, String>> itemsToFilter() {
        return getGradleProperties().mergeProperties(new HashMap<>()).entrySet().stream()
            .filter(e -> e.getValue() instanceof String)
            .map(e -> {
                Map.Entry<String, ?> untypedEntry = e;
                @SuppressWarnings("unchecked")
                Map.Entry<String, String> stringEntry = (Map.Entry<String, String>) untypedEntry;
                return stringEntry;
            });
    }
}
