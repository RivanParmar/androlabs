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

unassigned {
    subproject("gradle-build-events")
    subproject("gradle-build-operations")
    subproject("gradle-core")
    subproject("gradle-core-api")
    subproject("gradle-plugin-use")
}

buildTool("gradle-core-configuration") {
    subproject("base-services-groovy")
    subproject("file-collections")
    subproject("model-core")
    subproject("model-groovy")
}

buildTool("gradle-core-execution") {
    subproject("build-cache")
    subproject("build-cache-base")
    subproject("execution")
    subproject("file-watching")
    subproject("hashing")
    subproject("persistent-cache")
    subproject("snapshots")
    subproject("worker-processes")
}

buildTool("gradle-core-runtime") {
    subproject("base-annotations")
    subproject("base-services")
    subproject("build-option")
    subproject("cli")
    subproject("file-temp")
    subproject("files")
    subproject("functional")
    subproject("internal-instrumentation-api")
    subproject("logging")
    subproject("logging-api")
    subproject("messaging")
    subproject("native")
    subproject("process-services")
    subproject("wrapper-shared")
}

buildTool("gradle-enterprise") {
    subproject("enterprise-operations")
    subproject("enterprise-workers")
}

buildTool("gradle-ide") {
    subproject("problems")
}

buildTool("gradle-jvm") {
    subproject("jvm-services")
    subproject("normalization-java")
}

buildTool("gradle-software") {
    subproject("dependency-management")
    subproject("publish")
    subproject("reporting")
    subproject("resources")
    subproject("resources-http")
    subproject("security")
}

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