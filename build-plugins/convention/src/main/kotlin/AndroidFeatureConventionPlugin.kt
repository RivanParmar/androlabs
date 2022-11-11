import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("androidplaygrounds.android.library")

            dependencies {
                add("implementation", project(":core:designsystem"))
                // TODO: Add other commonly used libraries and modules here
            }
        }
    }
}