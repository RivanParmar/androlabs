package org.gradle.internal.buildoption;

import org.gradle.cli.CommandLineParser;
import org.gradle.cli.ParsedCommandLine;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Represents an option for a build provided by the user via Gradle property and/or a command line
 * option.
 *
 * @param <T> the type of object that ultimately expresses the option to consumers
 * @since 4.3
 */
public interface BuildOption<T> extends Option {

    @Nullable
    String getGradleProperty();

    void applyFromProperty(Map<String, String> properties, T settings);

    void configure(CommandLineParser parser);

    void applyFromCommandLine(ParsedCommandLine options, T settings);

    String getDeprecatedGradleProperty();
}