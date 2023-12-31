plugins {
    alias(libs.plugins.androlabs.jvm.library)
}

description = """JVM-specific test infrastructure, including support for bootstrapping and configuring test workers
and executing tests.
Few projects should need to depend on this module directly. Most external interactions with this module are through the
various implementations of WorkerTestClassProcessorFactory.
"""

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:testing-base"))

    implementation("org.testng:testng:7.8.0")
    implementation("org.apache-extras.beanshell:bsh:2.0b6") {
        because("Used by TestNG")
    }

    implementation(libs.slf4j)
    implementation(libs.apache.commons.lang3)
    implementation(libs.junit4)
}