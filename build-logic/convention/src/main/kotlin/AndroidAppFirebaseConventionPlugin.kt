import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidAppFirebaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                "implementation"(libs.findLibrary("firebase.analytics").get())
                "implementation"(libs.findLibrary("firebase.crashlytics").get())
            }

            extensions.configure<ApplicationAndroidComponentsExtension> {
                finalizeDsl {
                    it.buildTypes.forEach { buildType ->
                        buildType.configure<CrashlyticsExtension> {
                            // Disable the Crashlytics mapping file upload. This feature should only be
                            // enabled if a Firebase backend is available and configured in
                            // google-services.json.
                            mappingFileUploadEnabled = false
                        }
                    }
                }
            }
        }
    }
}