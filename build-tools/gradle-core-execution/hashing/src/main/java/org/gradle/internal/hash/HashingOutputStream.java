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

package org.gradle.internal.hash;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream decorator that hashes data written to the stream.
 * Inspired by the Google Guava project.
 */
public final class HashingOutputStream extends FilterOutputStream {
    private final PrimitiveHasher hasher;

    public HashingOutputStream(HashFunction hashFunction, OutputStream out) {
        super(checkNotNull(out));
        this.hasher = checkNotNull(hashFunction.newPrimitiveHasher());
    }

    @Override
    public void write(int b) throws IOException {
        hasher.putByte((byte) b);
        out.write(b);
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        hasher.putBytes(bytes, off, len);
        out.write(bytes, off, len);
    }

    public HashCode hash() {
        return hasher.hash();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
