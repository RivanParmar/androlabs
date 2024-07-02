import com.android.build.gradle.LibraryExtension
import com.rivan.androlabs.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("androlabs.android.library")
                apply("androlabs.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.rivan.androlabs.core.testing.AndroLabsTestRunner"
                }
                testOptions.animationsDisabled = true
            }

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:ui"))

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
            }
        }
    }
}