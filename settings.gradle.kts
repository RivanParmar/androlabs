pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven {
            name = "Gradle public repository"
            url = uri("https://repo.gradle.org/gradle/public")
        }
        maven {
            url = uri("https://releases.usethesource.io/maven/")
        }
        mavenCentral()
    }
}
rootProject.name = "androlabs"
include(":app")

include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:designsystem")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:testing")
include(":core:ui")

include(":feature:editor")
include(":feature:home")
include(":feature:nlw")
include(":feature:npw")
include(":feature:settings")

include(":sync:work")

include(":build-tools:gradle-base-services-groovy")
include(":build-tools:gradle-build-operations")

buildTool("gradle-core-execution") {
    subproject("hashing")
}

buildTool("gradle-core-runtime") {
    subproject("base-annotations")
    subproject("base-services")
    subproject("build-option")
    subproject("cli")
    subproject("file-temp")
    subproject("files")
    subproject("functional")
    subproject("logging-api")
    subproject("messaging")
    subproject("native")
    subproject("process-services")
}

include(":build-tools:builder-core")
include(":build-tools:build-logic")*/

include(":build-tools:project")*/

include(":platform:wizard:template-api")
include(":platform:wizard:template-impl")

include(":platform:core-api")

include(":platform:project-api")

include(":platform:language-api")

// region build-tools include DSL

fun buildTool(buildToolName: String, buildToolConfiguration: BuildToolsScope.() -> Unit) =
    BuildToolsScope("build-tools/$buildToolName").buildToolConfiguration()

fun unassigned(buildToolConfiguration: BuildToolsScope.() -> Unit) =
    BuildToolsScope("build-tools").buildToolConfiguration()

class BuildToolsScope(
    private val basePath: String
) {
    fun subproject(projectName: String) {
        include(":build-tools:$projectName")
        project(":build-tools:$projectName").projectDir = file("$basePath/$projectName")
    }
}

// endregion