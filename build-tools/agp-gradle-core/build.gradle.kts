plugins {
    alias(libs.plugins.androlabs.jvm.library)
    alias(libs.plugins.protobuf)
}

dependencies {
    implementation(project(":build-tools:aaptcompiler"))
    implementation(project(":build-tools:agp"))
    implementation(project(":build-tools:agp-gradle-api"))
    implementation(project(":build-tools:agp-test-api"))
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:dependency-management"))
    implementation(project(":build-tools:gradle-build-events"))
    implementation(project(":build-tools:gradle-core"))
    implementation(project(":build-tools:gradle-core-api"))
    implementation(project(":build-tools:gradle-diagnostics"))
    implementation(project(":build-tools:language-java"))
    implementation(project(":build-tools:language-jvm"))
    implementation(project(":build-tools:logging"))
    implementation(project(":build-tools:logging-api"))
    implementation(project(":build-tools:manifest-merger"))
    implementation(project(":build-tools:model-core"))
    implementation(project(":build-tools:platform-base"))
    implementation(project(":build-tools:platform-jvm"))
    implementation(project(":build-tools:plugins-java"))
    implementation(project(":build-tools:plugins-java-base"))
    implementation(project(":build-tools:publish"))
    implementation(project(":build-tools:reporting"))
    implementation(project(":build-tools:testing-base"))
    implementation(project(":build-tools:testing-jvm"))
    implementation(project(":build-tools:toolchains-jvm"))
    implementation(project(":build-tools:tooling-api"))
    implementation(project(":build-tools:workers"))

    implementation("androidx.databinding:databinding-common:8.3.0-alpha12")
    implementation("androidx.databinding:databinding-compiler-common:8.3.0-alpha12")
    implementation("com.android.databinding:baseLibrary:8.3.0-alpha12")

    implementation("com.android.tools.build:bundletool:1.15.5")

    implementation("com.android.tools.build.jetifier:jetifier-core:1.0.0-beta10")
    implementation("com.android.tools.build.jetifier:jetifier-processor:1.0.0-beta10")

    implementation("com.android.tools.lint:lint-model:31.3.0-alpha12")

    implementation("com.google.crypto.tink:tink-android:1.10.0")

    implementation("com.google.protobuf:protobuf-java-util:4.0.0-rc-2")

    implementation("com.squareup:javawriter:2.5.1")

    implementation("org.apache.maven.reporting:maven-reporting-api:4.0.0-M8")

    implementation("org.jacoco:jacoco-maven-plugin:0.8.11")

    implementation("org.jetbrains:annotations:24.0.1")

    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20") {
        exclude("org.gradle", "gradle-core-api")
    }
    compileOnly("org.jetbrains.dokka:dokka-core:1.9.10")

    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("org.glassfish.jaxb:jaxb-core:4.0.4")

    implementation(libs.android.zipflinger)
    implementation(libs.android.tools.build.apksig)
    implementation(libs.android.tools.build.apkzlib)
    implementation(libs.android.tools.common)
    implementation(libs.android.tools.layoutlib)
    implementation(libs.android.tools.r8)
    implementation(libs.android.tools.sdk.common)
    implementation(libs.android.tools.sdklib)
    implementation(libs.ant)
    implementation(libs.groovy)
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.javax.inject)
    implementation(libs.protobuf.java)
}

protobuf {
    protoc {
        artifact = libs.protobuf.java.protoc.get().toString()
    }
}

tasks.named("kotlinSourcesJar").configure {
    this.dependsOn(tasks.named("generateProto"))
}