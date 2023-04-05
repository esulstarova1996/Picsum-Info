plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        multiDexEnabled = true
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {

        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
        }
    }
}

dependencies {

    coreLibraryDesugaring(JdkLibs.desugarJdkLibs)

    implementation(project(Modules.photoDatasource))
    implementation(project(Modules.photoDomain))
    implementation(project(Modules.photoInteractors))
    implementation(project(Modules.ui_photoList))
    implementation(project(Modules.ui_photoDetails))

    implementation(Coil.coil)

    implementation(Ktor.core)
    implementation(Ktor.ktorKotlinxSerialization)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLay)
    implementation(AndroidX.lifecycleVmKtx)
    implementation(AndroidX.lifecycleExtensions)
    implementation(AndroidX.lifecycleRuntime)
    implementation(AndroidX.viewModelLifecycle)

    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUI)

    implementation(Hilt.android)
    kapt(Hilt.compiler)
}