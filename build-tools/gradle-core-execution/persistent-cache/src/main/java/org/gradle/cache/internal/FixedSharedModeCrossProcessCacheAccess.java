/*
 * Copyright 2014 the original author or authors.
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

package org.gradle.cache.internal;

import static org.gradle.cache.FileLockManager.LockMode.Exclusive;
import static org.gradle.cache.FileLockManager.LockMode.Shared;

import org.gradle.api.Action;
import org.gradle.cache.CacheOpenException;
import org.gradle.cache.CrossProcessCacheAccess;
import org.gradle.cache.FileLock;
import org.gradle.cache.FileLockManager;
import org.gradle.cache.LockOptions;
import org.gradle.internal.Factory;
import org.gradle.internal.UncheckedException;

import java.io.File;

/**
 * A {@link CrossProcessCacheAccess} implementation used when a cache is opened with a shared lock that is held until the cache is closed. The contract for {@link CrossProcessCacheAccess} requires an
 * exclusive lock be held, so this implementation simply throws 'not supported' exceptions for these methods.
 */
public class FixedSharedModeCrossProcessCacheAccess extends AbstractCrossProcessCacheAccess {
    private final String cacheDisplayName;
    private final File lockTarget;
    private final LockOptions lockOptions;
    private final FileLockManager lockManager;
    private final CacheInitializationAction initializationAction;
    private final Action<FileLock> onOpenAction;
    private final Action<FileLock> onCloseAction;
    private FileLock fileLock;

    public FixedSharedModeCrossProcessCacheAccess(String cacheDisplayName, File lockTarget, LockOptions lockOptions, FileLockManager lockManager, CacheInitializationAction initializationAction, Action<FileLock> onOpenAction, Action<FileLock> onCloseAction) {
        assert lockOptions.getMode() == Shared;
        this.cacheDisplayName = cacheDisplayName;
        this.lockTarget = lockTarget;
        this.lockOptions = lockOptions;
        this.lockManager = lockManager;
        this.initializationAction = initializationAction;
        this.onOpenAction = onOpenAction;
        this.onCloseAction = onCloseAction;
    }

    @Override
    public void open() {
        if (fileLock != null) {
            throw new IllegalStateException("File lock " + lockTarget + " is already open.");
        }
        FileLock fileLock = lockManager.lock(lockTarget, lockOptions, cacheDisplayName);
        try {
            boolean rebuild = initializationAction.requiresInitialization(fileLock);
            if (rebuild) {
                Exception latestException = null;
                for (int tries = 0; rebuild && tries < 3; tries++) {
                    fileLock.close();
                    fileLock = null;
                    FileLock exclusiveLock = null;
                    try {
                        exclusiveLock = lockManager.lock(lockTarget, lockOptions.withMode(Exclusive), cacheDisplayName);
                    } catch (Exception e) {
                        // acquiring the exclusive lock can fail in the rare case where another process is just doing or has just done the cache initialization
                        latestException = e;
                    }
                    try {
                        if (exclusiveLock != null) {
                            final FileLock acquiredExclusiveLock = exclusiveLock;
                            rebuild = initializationAction.requiresInitialization(acquiredExclusiveLock);
                            if (rebuild) {
                                exclusiveLock.writeFile(new Runnable() {
                                    @Override
                                    public void run() {
                                        initializationAction.initialize(acquiredExclusiveLock);
                                    }
                                });
                            }
                        }
                    } finally {
                        if (exclusiveLock != null) {
                            exclusiveLock.close();
                        }
                    }
                    fileLock = lockManager.lock(lockTarget, lockOptions, cacheDisplayName);
                    rebuild = initializationAction.requiresInitialization(fileLock);
                }
                if (rebuild) {
                    throw new CacheOpenException(String.format("Failed to initialize %s", cacheDisplayName), latestException);
                }
            }
            onOpenAction.execute(fileLock);
        } catch (Exception e) {
            if (fileLock != null) {
                fileLock.close();
            }
            throw UncheckedException.throwAsUncheckedException(e);
        }
        this.fileLock = fileLock;
    }

    @Override
    public void close() {
        if (fileLock != null) {
            try {
                onCloseAction.execute(fileLock);
                fileLock.close();
            } finally {
                fileLock = null;
            }
        }
    }

    @Override
    public Runnable acquireFileLock() {
        throw failure();
    }

    @Override
    public <T> T withFileLock(Factory<T> factory) {
        throw failure();
    }

    protected UnsupportedOperationException failure() {
        return new UnsupportedOperationException("Cannot escalate a shared lock to an exclusive lock. This is not yet supported.");
    }
}
