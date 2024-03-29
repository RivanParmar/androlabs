/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.android.builder.testing.api;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringTokenizer;

/**
 * Encapsulation of the device configuration obtained from "shell am get-config" command.
 */
public interface DeviceConfig {

    enum Category {
        CONFIG, ABI
    }

    @NonNull
    Optional<String> getValue(Category category);

    @NonNull
    List<String> getAbis();

    @NonNull
    String getConfigForAllAbis();

    @NonNull
    String getConfigFor(@Nullable String abi);

    class Builder {

        private static class Values {

            @NonNull
            private final Category myCategory;

            @NonNull
            private final String value;

            private Values(@NonNull Category category, @NonNull String value) {
                myCategory = category;
                this.value = value;
            }
        }

        public static DeviceConfig parse(Collection<String> lines) {
            ImmutableList.Builder<Values> valuesBuilder = ImmutableList.builder();
            for (String line : lines) {
                for (Category category : Category.values()) {
                    String key = category.name().toLowerCase(Locale.US) + ": ";
                    if (line.startsWith(key)) {
                        valuesBuilder.add(new Values(category, line.substring(key.length())));
                    }
                }
            }
            final ImmutableList<Values> values = valuesBuilder.build();
            return new DeviceConfig() {

                @Override
                @NonNull
                public Optional<String> getValue(Category category) {
                    for (Values value : values) {
                        if (value.myCategory.equals(category)) {
                            return Optional.of(value.value);
                        }
                    }
                    return Optional.empty();
                }

                @Override
                @NonNull
                public List<String> getAbis() {
                    ImmutableList.Builder<String> abiBuilder = ImmutableList.builder();
                    Optional<String> abis = getValue(Category.ABI);
                    if (abis.isPresent()) {
                        StringTokenizer stringTokenizer = new StringTokenizer(abis.get(), ",");
                        while (stringTokenizer.hasMoreElements()) {
                            abiBuilder.add(stringTokenizer.nextToken());
                        }
                    }
                    return abiBuilder.build();
                }

                @Override
                @NonNull
                public String getConfigForAllAbis() {
                    StringBuilder completeConfig = new StringBuilder();
                    Optional<String> config = getValue(Category.CONFIG);
                    List<String> abis = getAbis();
                    if (abis.isEmpty() && config.isPresent()) {
                        completeConfig.append(config.get());
                    } else {
                        if (config.isPresent()) {
                            completeConfig.append(config.get());
                            completeConfig.append(":");
                        }
                        Joiner.on(",").appendTo(completeConfig, abis);
                    }
                    return completeConfig.toString();
                }

                @Override
                @NonNull
                public String getConfigFor(@Nullable String abi) {
                    StringBuilder completeConfig = new StringBuilder();
                    Optional<String> config = getValue(Category.CONFIG);
                    if (config.isPresent()) {
                        completeConfig.append(config.get());
                        if (!Strings.isNullOrEmpty(abi)) {
                            completeConfig.append(":");
                        }
                    }
                    if (!Strings.isNullOrEmpty(abi)) {
                        completeConfig.append(abi);
                    }
                    return completeConfig.toString();
                }
            };
        }
    }
}
