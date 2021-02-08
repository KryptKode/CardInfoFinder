import extensions.buildConfigStringField
import extensions.getLocalProperty

plugins {
    androidApplication
    kotlinAndroid
    kotlinParcelize
    kotlinKapt
    daggerHilt
    jacoco
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        applicationId("com.kryptkode.cardinfofinder")
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes.forEach {
        it.buildConfigStringField("SCAN_CARD_API_KEY", getLocalProperty("SCAN_CARD_API_KEY"))
    }

    buildTypes {
        buildTypes {
            getByName(BuildTypes.RELEASE) {
                isMinifyEnabled = true
            }
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(Libs.kotlin_stdlib)

    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.kotlinx_coroutines_core)

    implementation(Libs.core_ktx)
    implementation(Libs.appcompat)
    implementation(Libs.constraintlayout)
    implementation(Libs.fragment_ktx)
    implementation(Libs.material)
    implementation(Libs.vectordrawable)
    implementation(Libs.swiperefreshlayout)
    implementation(Libs.multidex)

    implementation(Libs.pageindicatorview)

    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_runtime_ktx)
    implementation(Libs.lifecycle_common_java8)

    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)

    implementation(Libs.datastore_preferences)

    implementation(Libs.glide)
    kapt(Libs.com_github_bumptech_glide_compiler)

    // Dependency injection
    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)

    implementation(Libs.timber)

    implementation(Libs.retrofit)
    implementation(Libs.converter_moshi)
    implementation(Libs.retrofit_mock)
    implementation(Libs.logging_interceptor)
    implementation(Libs.moshi)
    kapt(Libs.moshi_kotlin_codegen)

    implementation(Libs.cardscan_ui)
    implementation(Libs.tensorflow_lite)

    debugImplementation(Libs.leakcanary_android)

    implementation(Libs.play_services_mlkit_text_recognition)
    implementation(Libs.camera_camera2)
    implementation(Libs.camera_lifecycle)
    implementation(Libs.camera_view)

    implementation(Libs.espresso_idling_resource)

    // unit testing
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.mockk)
    testImplementation(Libs.truth)
    testImplementation (Libs.robolectric)
    testImplementation(Libs.mockwebserver)
    testImplementation(Libs.turbine)
    testImplementation(Libs.kotlin_test_junit)
    testImplementation(Libs.kotlinx_coroutines_test) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }

    debugImplementation(Libs.fragment_testing)

    // instrumentation testing
    androidTestImplementation(Libs.kotlin_test_junit)
    androidTestImplementation(Libs.kotlinx_coroutines_test) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.espresso_contrib)
    androidTestImplementation(Libs.espresso_intents)

    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.mockwebserver)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.mockk)
    androidTestImplementation(Libs.mockk_android)
    androidTestImplementation(Libs.truth)
    androidTestImplementation(Libs.hilt_android_testing)
    kaptAndroidTest(Libs.hilt_android_compiler)
}
