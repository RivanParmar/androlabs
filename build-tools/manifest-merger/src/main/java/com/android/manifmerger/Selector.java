/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.android.manifmerger;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a selector to be able to identify manifest file xml elements.
 */
@Immutable
public class Selector {

    /**
     * local name for tools:selector attributes.
     */
    public static final String SELECTOR_LOCAL_NAME = "selector";

    @NotNull private final String mPackageName;
    @NotNull
    private final List<String> mPackages = new LinkedList<>();

    public Selector(@NotNull String packageName) {
        mPackageName = Preconditions.checkNotNull(packageName);
        mPackages.addAll(Arrays.asList(mPackageName.split(",")));
    }

    /**
     * Returns true if the passed element is "selected" by this selector. If so, any action this
     * selector decorated will be applied to the element.
     */
    boolean appliesTo(@NotNull XmlElement element) {
        Optional<XmlAttribute> packageName = element.getDocument().getPackage();
        return packageName.isPresent() && mPackageName.contains(packageName.get().getValue());
    }

    /**
     * Returns true if the passed resolver can resolve this selector, false otherwise.
     */
    boolean isResolvable(@NotNull KeyResolver<String> resolver) {
        for (String p : mPackages) {
            if (resolver.resolve(p) == null) return false;
        }
        return true;
    }

    @NotNull
    @Override
    public String toString() {
        return mPackageName;
    }
}
