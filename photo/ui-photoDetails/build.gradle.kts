apply {
    from("$rootDir/common-android-library.gradle")
}

plugins {
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies {

    "implementation"(project(Modules.photoDomain))
    "implementation"(project(Modules.photoInteractors))

    "implementation"(Coil.coil)

    "implementation"(AndroidX.fragmentKtx)
    "implementation"(AndroidX.lifecycleRuntime)
    "implementation"(AndroidX.viewModelLifecycle)

    "implementation"(Navigation.navigationFragment)
    "implementation"(Navigation.navigationUI)

}