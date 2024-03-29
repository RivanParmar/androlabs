/*
 * Copyright 2018 the original author or authors.
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

package org.gradle.internal.execution.steps;

import org.gradle.internal.Try;
import org.gradle.internal.execution.ExecutionEngine;
import org.gradle.internal.execution.history.AfterExecutionState;

import java.time.Duration;
import java.util.Optional;

import javax.annotation.Nullable;

public class AfterExecutionResult extends Result {
    private final AfterExecutionState afterExecutionState;

    public AfterExecutionResult(Duration duration, Try<ExecutionEngine.Execution> execution, @Nullable AfterExecutionState afterExecutionState) {
        super(duration, execution);
        this.afterExecutionState = afterExecutionState;
    }

    public AfterExecutionResult(Result parent, @Nullable AfterExecutionState afterExecutionState) {
        super(parent);
        this.afterExecutionState = afterExecutionState;
    }

    protected AfterExecutionResult(AfterExecutionResult parent) {
        this(parent, parent.getAfterExecutionState().orElse(null));
    }

    /**
     * State after execution, or {@link Optional#empty()} if work is untracked.
     */
    public Optional<AfterExecutionState> getAfterExecutionState() {
        return Optional.ofNullable(afterExecutionState);
    }
}
