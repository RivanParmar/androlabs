/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.android.builder.utils;

import com.android.annotations.NonNull;

import java.util.function.Consumer;

/**
 * Consumer that can throw an {@link Exception}.
 */
@FunctionalInterface
public interface ExceptionConsumer<T> {

    /**
     * Performs an operation on the given input.
     *
     * @param input the input
     */
    void accept(@NonNull T input) throws Exception;

    /**
     * Wraps an {@link ExceptionConsumer} into a {@link Consumer} by throwing a
     * {@link RuntimeException}.
     *
     * @param exceptionConsumer the consumer that can throw an exception
     */
    @NonNull
    static <T> Consumer<T> asConsumer(@NonNull ExceptionConsumer<T> exceptionConsumer)  {
        return input -> {
            try {
                exceptionConsumer.accept(input);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
