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
include("app")
include("android-compiler")
include("core-navigation")
include("feature-recents")
include("jaxp:internal")
include("jaxp:xml")
include("kotlinc")
include("project-utils")
