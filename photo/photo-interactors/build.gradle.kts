apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies {

    "implementation"(project(Modules.photoDomain))
    "implementation"(project(Modules.photoDatasource))

    "implementation"(Kotlinx.coroutinesCore)
}