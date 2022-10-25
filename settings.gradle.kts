pluginManagement {
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
        mavenCentral()
    }
}
rootProject.name = "Android Playgrounds"
include(":app")
include(":core:navigation")
include(":feature:recents")

include(":build-tools:javac")
include(":build-tools:jaxp:internal")
include(":build-tools:jaxp:xml")
include(":build-tools:kotlinc")

include(":build-tools:builder-core")
include(":build-tools:build-logic")

include(":build-tools:project")
