/*
 * Copyright 2020 the original author or authors.
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

package org.gradle.jvm.toolchain.internal.task;

import static org.gradle.api.internal.lambdas.SerializableLambdas.spec;
import static org.gradle.internal.logging.text.StyledTextOutput.Style.Description;
import static org.gradle.internal.logging.text.StyledTextOutput.Style.Identifier;
import static org.gradle.internal.logging.text.StyledTextOutput.Style.Normal;

import com.google.common.base.Strings;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.UntrackedTask;
import org.gradle.internal.jvm.inspection.JavaInstallationRegistry;
import org.gradle.internal.jvm.inspection.JvmToolchainMetadata;
import org.gradle.internal.logging.text.StyledTextOutput;
import org.gradle.internal.logging.text.StyledTextOutputFactory;
import org.gradle.jvm.toolchain.internal.AutoDetectingInstallationSupplier;
import org.gradle.jvm.toolchain.internal.install.DefaultJavaToolchainProvisioningService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

@UntrackedTask(because = "Produces only non-cacheable console output")
public abstract class ShowToolchainsTask extends DefaultTask {

    private static final Comparator<JvmToolchainMetadata> TOOLCHAIN_COMPARATOR = Comparator
        .<JvmToolchainMetadata, String>comparing(t -> t.metadata.getDisplayName())
        .thenComparing(t -> t.metadata.getLanguageVersion());

    private final ToolchainReportRenderer toolchainRenderer = new ToolchainReportRenderer();

    public ShowToolchainsTask() {
        getOutputs().upToDateWhen(spec(element -> false));
    }

    @TaskAction
    public void showToolchains() {
        StyledTextOutput output = getTextOutputFactory().create(getClass());
        toolchainRenderer.setOutput(output);
        output.println();
        List<JvmToolchainMetadata> toolchains = allReportableToolchains();
        List<JvmToolchainMetadata> validToolchains = validToolchains(toolchains);
        List<JvmToolchainMetadata> invalidToolchains = invalidToolchains(toolchains);
        printOptions(output);
        validToolchains.forEach(toolchainRenderer::printToolchain);
        toolchainRenderer.printInvalidToolchains(invalidToolchains);
    }

    private void printOptions(StyledTextOutput output) {
        boolean detectionEnabled = getBooleanProperty(AutoDetectingInstallationSupplier.AUTO_DETECT);
        boolean downloadEnabled = getBooleanProperty(DefaultJavaToolchainProvisioningService.AUTO_DOWNLOAD);
        output.withStyle(Identifier).println(" + Options");
        output.withStyle(Normal).format("     | %s", Strings.padEnd("Auto-detection:", 20, ' '));
        output.withStyle(Description).println(detectionEnabled ? "Enabled" : "Disabled");
        output.withStyle(Normal).format("     | %s", Strings.padEnd("Auto-download:", 20, ' '));
        output.withStyle(Description).println(downloadEnabled ? "Enabled" : "Disabled");
        output.println();
    }

    private Boolean getBooleanProperty(String propertyKey) {
        return getProviderFactory().gradleProperty(propertyKey).map(Boolean::parseBoolean).getOrElse(true);
    }

    private List<JvmToolchainMetadata> invalidToolchains(List<JvmToolchainMetadata> toolchains) {
        return toolchains.stream().filter(t -> !isValidToolchain().test(t)).collect(Collectors.toList());
    }

    private List<JvmToolchainMetadata> validToolchains(Collection<JvmToolchainMetadata> toolchains) {
        return toolchains.stream().filter(isValidToolchain()).sorted(TOOLCHAIN_COMPARATOR).collect(Collectors.toList());
    }

    private Predicate<? super JvmToolchainMetadata> isValidToolchain() {
        return t -> t.metadata.isValidInstallation();
    }

    private List<JvmToolchainMetadata> allReportableToolchains() {
        return getInstallationRegistry()
            .toolchains();
    }

    @Inject
    protected JavaInstallationRegistry getInstallationRegistry() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected StyledTextOutputFactory getTextOutputFactory() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected ProviderFactory getProviderFactory() {
        throw new UnsupportedOperationException();
    }

}
