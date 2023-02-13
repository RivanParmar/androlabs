pluginManagement {
    includeBuild("build-plugins")
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
rootProject.name = "Andro Labs"
include(":app")

include(":core:designsystem")
include(":core:model")
include(":core:ui")

include(":feature:recent")
include(":feature:settings")

include(":build-tools:javac")
include(":build-tools:jaxp:internal")
include(":build-tools:jaxp:xml")
include(":build-tools:kotlinc")

include(":build-tools:builder-core")
include(":build-tools:build-logic")

include(":build-tools:project")
