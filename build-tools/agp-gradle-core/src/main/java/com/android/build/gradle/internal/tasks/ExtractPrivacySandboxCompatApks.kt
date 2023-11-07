/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.android.build.gradle.internal.tasks

import com.android.build.api.variant.impl.BuiltArtifactImpl
import com.android.build.api.variant.impl.BuiltArtifactsImpl
import com.android.build.gradle.internal.AndroidJarInput
import com.android.build.gradle.internal.LoggerWrapper
import com.android.build.gradle.internal.component.ApkCreationConfig
import com.android.build.gradle.internal.component.ComponentCreationConfig
import com.android.build.gradle.internal.initialize
import com.android.build.gradle.internal.res.namespaced.Aapt2LinkRunnable
import com.android.build.gradle.internal.scope.InternalArtifactType
import com.android.build.gradle.internal.services.Aapt2Input
import com.android.build.gradle.internal.services.getLeasingAapt2
import com.android.build.gradle.internal.signing.SigningConfigData
import com.android.build.gradle.internal.signing.SigningConfigDataProvider
import com.android.build.gradle.internal.tasks.factory.VariantTaskCreationAction
import com.android.build.gradle.internal.utils.setDisallowChanges
import com.android.build.gradle.options.BooleanOption
import com.android.buildanalyzer.common.TaskCategory
import com.android.builder.core.ComponentTypeImpl
import com.android.builder.internal.aapt.AaptOptions
import com.android.builder.internal.aapt.AaptPackageConfig
import com.android.builder.internal.packaging.ApkFlinger
import com.android.ide.common.signing.KeystoreHelper
import com.android.ide.common.xml.XmlPrettyPrinter
import com.android.tools.build.apkzlib.sign.SigningOptions
import com.android.tools.build.apkzlib.zfile.ApkCreatorFactory
import com.android.tools.build.apkzlib.zfile.NativeLibrariesPackagingMode
import com.android.utils.FileUtils
import com.google.common.base.Charsets
import com.google.common.collect.ImmutableList
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskProvider
import org.gradle.work.DisableCachingByDefault
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import java.nio.file.Path
import java.util.zip.Deflater
import java.util.zip.ZipFile
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Task to produce a directory containing .apks files generated from Privacy Sandbox ASAR files.
 *
 * The apks files are used for deploying applications using Privacy Sandbox to device that do not
 * have support for the SandboxSdkManager service.
 */
@DisableCachingByDefault
@BuildAnalyzer(primaryTaskCategory = TaskCategory.APK_PACKAGING)
abstract class ExtractPrivacySandboxCompatApks: NonIncrementalTask() {

    @get:Classpath
    abstract val privacySandboxSplitCompatApks: ConfigurableFileCollection

    @get:InputFile
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val runtimeEnabledSdkTableFile: RegularFileProperty

    @get:Input
    abstract val privacySandboxEnabled: Property<Boolean>

    @get:Input
    abstract val applicationId: Property<String>

    @get:Nested
    abstract val androidJarInput: AndroidJarInput

    @get:Nested
    abstract val aapt2: Aapt2Input

    @get:Nested
    abstract val signingConfig: Property<SigningConfigDataProvider>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:OutputFile
    abstract val apksFromBundleIdeModel: RegularFileProperty

    override fun doTaskAction() {
        if (!privacySandboxEnabled.get()) {
            throw IllegalStateException(
                    "Unable to execute task as Privacy Sandbox support is not enabled. \n" +
                            "To enable support, add\n" +
                            "    ${BooleanOption.PRIVACY_SANDBOX_SDK_SUPPORT.propertyName}=true\n" +
                            "to your project's gradle.properties file."
            )
        }

        val elements = mutableListOf<BuiltArtifactImpl>()

        privacySandboxSplitCompatApks.files.forEach { zippedApks ->
            ZipFile(zippedApks).use { apks ->
                if (apks.getEntry("toc.pb") != null) {
                    throw IllegalStateException(
                            "Apks ${zippedApks.absolutePath} must not contain a toc.pb file.")
                }
                apks.entries().asIterator().forEach {
                    if (it.name.contains(".apk")) {
                        val apkBytes = apks.getInputStream(it).readBytes()
                        val outputApkFile = File(outputDir.get().asFile, it.name)
                        FileUtils.createFile(outputApkFile, "")
                        outputApkFile.writeBytes(apkBytes)
                        elements.add(BuiltArtifactImpl.make(outputFile = outputApkFile.absolutePath))
                    }
                }
            }
        }

        if (privacySandboxSplitCompatApks.files.isNotEmpty()) {
            // Generate a split for packaging assets required for Privacy Sandbox compatibility with
            // unsupported platforms.
            val compatSplitApk = writeCompatApkSplit(
                    FileUtils.join(outputDir.get().asFile,
                            "splits",
                            "${
                                applicationId.get()
                                        .replace(".", "")
                            }-injected-privacy-sandbox-compat.apk")
                            .toPath(),
                    temporaryDir,
                    runtimeEnabledSdkTableFile.get().asFile)
            elements.add(BuiltArtifactImpl.make(outputFile = compatSplitApk.toAbsolutePath()
                    .toString()))
        }

        writeMetadata(elements)
    }

    /**
    When deploying via APK in Studio, we need to package the RuntimeEnabledSdkTable asset
    [InternalArtifactType.RUNTIME_ENABLED_SDK_TABLE] required by
    Androidx Privacy Sandbox libraries for backwards compatibility for devices that do not support
    privacy sandbox. Therefore, we need to create a split APK just containing this asset.
     **/
    private fun writeCompatApkSplit(
            outputApkPath: Path,
            tempDir: File, runtimeEnabledSdkTableFile: File): Path {
        val signingConfigData: SigningConfigData = signingConfig.get().signingConfigData.get()
                ?: throw RuntimeException("SigningConfig is not defined.")
        val certificateInfo = KeystoreHelper.getCertificateInfo(
                signingConfigData.storeType,
                signingConfigData.storeFile,
                signingConfigData.storePassword,
                signingConfigData.keyPassword,
                signingConfigData.keyAlias)
        val signingOptions = SigningOptions.builder()
                .setKey(certificateInfo.key)
                .setCertificates(certificateInfo.certificate)
                .setMinSdkVersion(1)
                .setV1SigningEnabled(true)
                .setV2SigningEnabled(true)
                .build()
        val creationData =
                ApkCreatorFactory.CreationData.builder()
                        .setNativeLibrariesPackagingMode(NativeLibrariesPackagingMode.COMPRESSED)
                        .setApkPath(outputApkPath.toFile())
                        .setSigningOptions(signingOptions)
                        .build()

        val apkDir =
                FileUtils.join(tempDir, "tmp-apk-compat-privacy-sandbox-split").apply { mkdirs() }
        val manifest = File(apkDir, "AndroidManifest.xml")
        val manifestText =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<manifest \n" +
                        " xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                        " android:isFeatureSplit=\"true\"\n" +
                        " android:versionCode=\"1\"\n" +
                        " split=\"${outputApkPath.toFile().nameWithoutExtension.replace("-", "")}\"\n" +
                        " package=\"${applicationId.get()}\">\n" +
                        "<application android:hasCode=\"false\"></application>\n" +
                        "</manifest>"
        val documentFactory = DocumentBuilderFactory.newInstance()
        val xmlDocBuilder: DocumentBuilder = documentFactory.newDocumentBuilder()
        val manifestDocument = xmlDocBuilder.parse(InputSource(StringReader(manifestText)))
        val prettyPrintManifest = XmlPrettyPrinter.prettyPrint(manifestDocument, true)
        com.google.common.io.Files.asCharSink(manifest, Charsets.UTF_8)
                .write(prettyPrintManifest)

        val config = AaptPackageConfig(
                androidJarPath = androidJarInput.getAndroidJar().get().absolutePath,
                generateProtos = false,
                manifestFile = manifest,
                options = AaptOptions(null, null),
                resourceOutputApk = outputApkPath.toFile(),
                componentType = ComponentTypeImpl.BASE_APK,
                packageId = null,
                resourceDirs = ImmutableList.of()
        )
        aapt2.getLeasingAapt2()
                .link(config,
                        LoggerWrapper(Logging.getLogger(Aapt2LinkRunnable::class.java)))

        ApkFlinger(creationData,
                Deflater.BEST_SPEED,
                true,
                enableV3Signing = true,
                enableV4Signing = true).use { apkCreator ->
            apkCreator.writeFile(runtimeEnabledSdkTableFile,
                    "assets/${runtimeEnabledSdkTableFile.name}")
        }
        return outputApkPath
    }

    private fun writeMetadata(elementList: MutableList<BuiltArtifactImpl>) {
        BuiltArtifactsImpl(
                artifactType = InternalArtifactType.EXTRACTED_SDK_APKS,
                applicationId = applicationId.get(),
                variantName = "",
                elements = elementList

        ).saveToFile(apksFromBundleIdeModel.get().asFile)
    }

    class CreationAction(creationAction: ApkCreationConfig) :
            VariantTaskCreationAction<ExtractPrivacySandboxCompatApks, ApkCreationConfig>(creationAction) {

        override val name: String
            get() = getTaskName(creationConfig)
        override val type: Class<ExtractPrivacySandboxCompatApks>
            get() = ExtractPrivacySandboxCompatApks::class.java

        override fun handleProvider(
                taskProvider: TaskProvider<ExtractPrivacySandboxCompatApks>
        ) {
            super.handleProvider(taskProvider)
            creationConfig.artifacts.setInitialProvider(
                    taskProvider,
                    ExtractPrivacySandboxCompatApks::outputDir)
                    .on(InternalArtifactType.EXTRACTED_SDK_APKS)
            creationConfig.artifacts.setInitialProvider(
                    taskProvider,
                    ExtractPrivacySandboxCompatApks::apksFromBundleIdeModel)
                    .withName(BuiltArtifactsImpl.METADATA_FILE_NAME)
                    .on(InternalArtifactType.APK_FROM_SDKS_IDE_MODEL)
        }

        override fun configure(task: ExtractPrivacySandboxCompatApks) {
            super.configure(task)
            task.privacySandboxSplitCompatApks.from(
                    creationConfig.artifacts.get(InternalArtifactType.SDK_SPLITS_APKS)
                            .map { it.asFileTree }
            )
            task.runtimeEnabledSdkTableFile.setDisallowChanges(
                    creationConfig.artifacts.get(InternalArtifactType.RUNTIME_ENABLED_SDK_TABLE)
            )
            task.applicationId.setDisallowChanges(creationConfig.applicationId)
            task.privacySandboxEnabled.setDisallowChanges(
                    creationConfig.services.projectOptions[BooleanOption.PRIVACY_SANDBOX_SDK_SUPPORT])
            task.androidJarInput.initialize(creationConfig)
            creationConfig.services.initializeAapt2Input(task.aapt2)
            task.signingConfig.setDisallowChanges(SigningConfigDataProvider.create(creationConfig))
        }

        companion object {
            fun getTaskName(creationAction: ComponentCreationConfig): String {
                return creationAction.computeTaskName("extractApksFromSdkSplitsFor")
            }
        }
    }
}
