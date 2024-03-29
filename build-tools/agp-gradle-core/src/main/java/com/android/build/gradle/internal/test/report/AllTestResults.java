/*
 * Copyright 2011 the original author or authors.
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

package com.android.build.gradle.internal.test.report;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Custom test results based on Gradle's AllTestResults
 */
class AllTestResults extends CompositeTestResults {
    private final Map<String, PackageTestResults> packages = new TreeMap<>();

    public AllTestResults() {
        super(null);
    }

    @Override
    public String getTitle() {
        return "Test Summary";
    }

    public Collection<PackageTestResults> getPackages() {
        return packages.values();
    }

    @Override
    public String getName() {
        return null;
    }

    public TestResult addTest(String className, String testName, long duration,
                              String device, String project, String flavor) {
        PackageTestResults packageResults = addPackageForClass(className);
        TestResult testResult = addTest(
                packageResults.addTest(className, testName, duration, device, project, flavor));

        addDevice(device, testResult);
        addVariant(project, flavor, testResult);

        return testResult;
    }

    public TestResult addTest(String className, String testName, long duration, String device, String project, String flavor, ScreenshotTestImages ssImages) {
        PackageTestResults packageResults = addPackageForClass(className);
        TestResult testResult = addTest(
                packageResults.addTest(className, testName, duration, device, project, flavor, ssImages));

        addDevice(device, testResult);
        addVariant(project, flavor, testResult);

        return testResult;
    }

    public ClassTestResults addTestClass(String className) {
        return addPackageForClass(className).addClass(className);
    }

    private PackageTestResults addPackageForClass(String className) {
        String packageName;
        int pos = className.lastIndexOf(".");
        if (pos != -1) {
            packageName = className.substring(0, pos);
        } else {
            packageName = "";
        }
        return addPackage(packageName);
    }

    private PackageTestResults addPackage(String packageName) {

        PackageTestResults packageResults = packages.get(packageName);
        if (packageResults == null) {
            packageResults = new PackageTestResults(packageName, this);
            packages.put(packageName, packageResults);
        }
        return packageResults;
    }
}
