apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies {

    "implementation"(project(Modules.photoDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.android)
    "implementation"(Ktor.ktorKotlinxSerialization)
    "implementation"(Ktor.contentNegotiator)
    "implementation"(Ktor.ktorClientLogging)
}