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

package org.gradle.api.plugins.internal;

import static org.gradle.api.reflect.TypeOf.typeOf;

import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.plugins.ApplicationPluginConvention;
import org.gradle.api.reflect.HasPublicType;
import org.gradle.api.reflect.TypeOf;
import org.gradle.internal.deprecation.DeprecationLogger;

import java.util.ArrayList;

import javax.inject.Inject;

public abstract class DefaultApplicationPluginConvention extends ApplicationPluginConvention implements HasPublicType {
    private String applicationName;
    private String mainClassName;
    private Iterable<String> applicationDefaultJvmArgs = new ArrayList<String>();
    private String executableDir = "bin";
    private CopySpec applicationDistribution;

    private final Project project;

    @Inject
    public DefaultApplicationPluginConvention(Project project) {
        this.project = project;
        applicationDistribution = project.copySpec();
    }

    @Override
    public TypeOf<?> getPublicType() {
        return typeOf(ApplicationPluginConvention.class);
    }

    @Override
    public String getApplicationName() {
        logDeprecation();
        return applicationName;
    }

    @Override
    public void setApplicationName(String applicationName) {
        logDeprecation();
        this.applicationName = applicationName;
    }

    @Override
    public String getMainClassName() {
        logDeprecation();
        return mainClassName;
    }

    @Override
    public void setMainClassName(String mainClassName) {
        logDeprecation();
        this.mainClassName = mainClassName;
    }

    @Override
    public Iterable<String> getApplicationDefaultJvmArgs() {
        logDeprecation();
        return applicationDefaultJvmArgs;
    }

    @Override
    public void setApplicationDefaultJvmArgs(Iterable<String> applicationDefaultJvmArgs) {
        logDeprecation();
        this.applicationDefaultJvmArgs = applicationDefaultJvmArgs;
    }

    @Override
    public String getExecutableDir() {
        logDeprecation();
        return executableDir;
    }

    @Override
    public void setExecutableDir(String executableDir) {
        logDeprecation();
        this.executableDir = executableDir;
    }

    @Override
    public CopySpec getApplicationDistribution() {
        logDeprecation();
        return applicationDistribution;
    }

    @Override
    public void setApplicationDistribution(CopySpec applicationDistribution) {
        logDeprecation();
        this.applicationDistribution = applicationDistribution;
    }

    @Override
    public Project getProject() {
        logDeprecation();
        return project;
    }

    private static void logDeprecation() {
        DeprecationLogger.deprecateType(ApplicationPluginConvention.class)
            .willBeRemovedInGradle9()
            .withUpgradeGuideSection(8, "application_convention_deprecation")
            .nagUser();
    }
}
