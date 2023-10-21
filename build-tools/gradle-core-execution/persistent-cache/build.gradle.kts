plugins {
    id("androlabs.jvm.library")
}

description = """Persistent caches on disk and cross process locking.
    | Mostly for persisting Maps to the disk.
    | Also contains implementations for in-memory caches in front of the disk cache.
""".trimMargin()

dependencies {
    implementation(project(":build-tools:base-services"))
    implementation(project(":build-tools:messaging"))
    implementation(project(":build-tools:native"))
    implementation(project(":build-tools:files"))
    implementation(project(":build-tools:resources"))
    implementation(project(":build-tools:logging"))

    implementation(libs.apache.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.guava)
    implementation(libs.slf4j)
}